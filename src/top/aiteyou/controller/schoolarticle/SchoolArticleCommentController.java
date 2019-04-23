package top.aiteyou.controller.schoolarticle;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.service.JudgeUserIsOnDisableSendMsgService;
import top.aiteyou.service.TemplateInfoInformService;
import top.aiteyou.service.schoolarticle.ArticleCommentService;
import top.aiteyou.service.schoolarticle.SchoolArticleTemplateMsgService;
import top.aiteyou.textscan.TextAntispamScanSample;
import top.aiteyou.transform.DisableInfo;
import top.aiteyou.transform.schoolarticle.ArticleCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;
import top.aiteyou.websocket.ArticleWebSocketService;

/**
 *  校园动态评论
	*@author :tb
	*@version 2018年4月18日 下午8:13:10
*/
@Controller
@RequestMapping(value="/schoolarticle/articlecomments")
public class SchoolArticleCommentController {
	@Autowired
	private ArticleCommentService articleCommentService=null;
	//校园动态 评论回复 模板消息 事务
	@Autowired
	private SchoolArticleTemplateMsgService templateInfoInformService=null;
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private ArticleWebSocketService webSocketService=null;
	
	private static final String KindOf="ArticleComment";
	@Autowired
	private AdminMsgOfNotLegalCommentWebSocketService adminMsgSocketService=null;
	@Autowired
	private JudgeUserIsOnDisableSendMsgService judgeUserIsDisableSendMsg=null;
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public int cancleArticleCommentShow(@RequestBody ArticleComment articleComment){
		return articleCommentService.cancleCommentShow(articleComment);
	}
	
	/**
	 * 校园评论点赞
	 * @param commentLike
	 * @return
	 */
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.POST)
	@ResponseBody
	public int addLikeCount(@RequestBody ArticleCommentLike commentLike){
		return articleCommentService.addLikeCount(commentLike);
	}
	/**
	 * 评论详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ArticleComment getOneDetail(@PathVariable("id") Integer id){
		return articleCommentService.getOneDetail(id);
	}
	
	
	/**
	 * 校圈动态添加一条评论，异步通知作者 异步更新动态评论数
	 * 清除动态详情缓存
	 * @param articleComment
	 * @return
	 */
	@RequestMapping(value="/app{appId}",method=RequestMethod.POST)
	@ResponseBody
	public ArticleComment insert(@PathVariable("appId")int appId,@RequestBody ArticleComment articleComment){
		int userId=articleComment.getUserId();
		// 如果用户禁言中
		DisableInfo disableInfo=judgeUserIsDisableSendMsg.judgeUserIsDisableSendMsg(userId);
		if (disableInfo.isDisable()) {
			articleComment.setStatus(2);
			StringBuffer content=new StringBuffer();
			content.append(DisableInfo.DisableText);
			content.append("距离解封时间还有"+disableInfo.getDiffTime());
			articleComment.setText(content.toString());
			return articleComment;
		}
		User user=userMapper.selectBaseData(userId);
		articleComment.setUser(user);
		articleComment.setTime(new Timestamp(System.currentTimeMillis()));
		articleComment.setLikecount(0);
		int articleId=articleComment.getArticleId();
		//文字审核 请求第三方api
		boolean notlegal=TextAntispamScanSample.scanText(articleComment.getText());
		Integer result=0;
		if (notlegal) {//如果不合法
			//设置非法性
			articleComment.setStatus(0);
			articleCommentService.insertBlockNewsComment(articleComment);
			
			//异步发送 socket 给管理员
			AdminMsg adminMsg=new AdminMsg(articleComment.getUserId(), articleComment.getText(), articleComment.getTime(),KindOf ,user);
			adminMsgSocketService.saveAndSendAdminMsgOfNotLegalComment(adminMsg);
			//异步检查 同一个赛事下非法评论是否 超过3  尝试发送模板消息给管理员
			articleCommentService.checkNotLegalCountAndSend(appId,articleComment.getArticleId());
			
		}else {
			articleComment.setStatus(1);
			result=articleCommentService.insertComment(articleComment);
			
		}
		if (result==1) {//审核过了
			//异步模板消息通知  加入策略机制 选择发送
//			templateInfoInformService.schoolArticleCommentReply(articleComment);
			
			//异步使用 websocket
			webSocketService.saveAndSendArticleCommentMsg(articleComment);
			//异步更新下动态的评论数
			articleCommentService.updateArticleCommentCount(articleId);
		}
		return articleComment;
	}
	
	
	
}


