package top.aiteyou.controller.schoolarticle;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.service.JudgeUserIsOnDisableSendMsgService;
import top.aiteyou.service.schoolarticle.ArticleCommentToCommentService;
import top.aiteyou.service.schoolarticle.SchoolArticleTemplateMsgService;
import top.aiteyou.textscan.TextAntispamScanSample;
import top.aiteyou.transform.DisableInfo;
import top.aiteyou.transform.schoolarticle.ArticleCommentToCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;
import top.aiteyou.websocket.ArticleCommentWebSocketService;

/**
	*@author :tb
	*@version 2018年4月22日 下午2:28:02
*/
@Controller
@RequestMapping(value="/schoolarticle/commenttocomments")
public class SchoolArticleCommentToCommentController {
	
	@Autowired
	private ArticleCommentToCommentService commentToCommentService=null;
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private SchoolArticleTemplateMsgService templateInfoInformService=null;
	@Autowired
	private ArticleCommentWebSocketService articleCommentSocketService=null;
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	
	private static final String KindOf="ArticleCommentToComment";
	@Autowired
	private AdminMsgOfNotLegalCommentWebSocketService adminMsgSocketService=null;
	@Autowired
	private JudgeUserIsOnDisableSendMsgService judgeUserIsDisableSendMsg=null;
	/**
	 * 评论的评论点赞
	 * @param like
	 * @return
	 */
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.POST)
	@ResponseBody
	public int addLikeCount(@RequestBody ArticleCommentToCommentLike like ){
		return commentToCommentService.addLikeCount(like);
	}
	
	/**
	 * 添加评论的评论
	 * @return
	 */
	@RequestMapping(value="/app{appId}",method=RequestMethod.POST)
	@ResponseBody
	public ArticleCommentToComment insertOne(@PathVariable("appId")int appId,@RequestBody ArticleCommentToComment articleCommentToComment){
		int userId=articleCommentToComment.getUserId();
		
		// 如果用户禁言中
		DisableInfo disableInfo=judgeUserIsDisableSendMsg.judgeUserIsDisableSendMsg(userId);
		if (disableInfo.isDisable()) {
			articleCommentToComment.setStatus(2);
			StringBuffer content=new StringBuffer();
			content.append(DisableInfo.DisableText);
			content.append("距离解封时间还有"+disableInfo.getDiffTime());
			articleCommentToComment.setContent(content.toString());
			return articleCommentToComment;
		}
		User user=userMapper.selectBaseData(userId);
		articleCommentToComment.setUser(user);
		articleCommentToComment.setTime(new Timestamp(System.currentTimeMillis()));
		articleCommentToComment.setLikecount(0);
		//文字审核 请求第三方api
		boolean notlegal=TextAntispamScanSample.scanText(articleCommentToComment.getContent());
		Integer result=0;
		if (notlegal) {//如果不合法
			//设置非法性
			articleCommentToComment.setStatus(0);
			commentToCommentService.insertOneNotLegal(articleCommentToComment);
			
			//异步发送 socket 给管理员
			AdminMsg adminMsg=new AdminMsg(articleCommentToComment.getUserId(), articleCommentToComment.getContent(), 
					articleCommentToComment.getTime(),KindOf,user );
			adminMsgSocketService.saveAndSendAdminMsgOfNotLegalComment(adminMsg);
			//异步检查 同一个动态评论下的非法评论是否 超过3  尝试发送模板消息给管理员
			commentToCommentService.checkNotLegalCountAndSend(appId,articleCommentToComment.getCommentId());
		}else {
			articleCommentToComment.setStatus(1);
			ArticleComment articleComment=articleCommentMapper.selectBaseData(articleCommentToComment.getCommentId());
			Integer articleId=articleComment.getArticleId();
			result=commentToCommentService.insertOneLegal(articleId,articleCommentToComment);
		}
		if (result==1) {//审核过了
			//异步通知 模板消息
//			templateInfoInformService.schoolArticleCommentToCommentReply(articleCommentToComment);
			
			//异步 使用socket 管理员消息
			articleCommentSocketService.saveAndSendArticleCommentToCommentMsg(articleCommentToComment);
			//异步更新校园评论的评论数
			commentToCommentService.updateArticleCommentcommentCount(articleCommentToComment.getCommentId());
		}
		return articleCommentToComment;
	}
}


