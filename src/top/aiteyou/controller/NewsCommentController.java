package top.aiteyou.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.NewsComment;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.service.AdminMsgTemplateService;
import top.aiteyou.service.JudgeUserIsOnDisableSendMsgService;
import top.aiteyou.service.NewsCommentService;
import top.aiteyou.service.NewsService;
import top.aiteyou.textscan.TextAntispamScanSample;
import top.aiteyou.transform.DisableInfo;
import top.aiteyou.transform.NewsCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;

/**
	*@author :tb
	*@version 2018年3月28日 下午4:16:12
*/
@Controller
@RequestMapping(value="/newscomments")
public class NewsCommentController {
	@Autowired
	private NewsCommentService  newsCommentService=null;
	
	@Autowired
	private NewsService newsService=null;
	@Autowired
	private AdminMsgOfNotLegalCommentWebSocketService adminMsgSocketService=null;
	private static final String KinfOf="NewsComment";
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private JudgeUserIsOnDisableSendMsgService judgeUserIsDisableSendMsg=null;
	
	/**添加资讯评论 并审核内容
	 * @param newsId 
	 * @param userId
	 * @param content
	 * @param likecount
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="/app{appId}",method=RequestMethod.POST)
	@ResponseBody
	public NewsComment insertNewsComment(@PathVariable("appId")int appId,@RequestBody NewsComment newsComment) throws InterruptedException{
		int userId=newsComment.getUserId();
		// 如果用户禁言中
		DisableInfo disableInfo=judgeUserIsDisableSendMsg.judgeUserIsDisableSendMsg(userId);
		if (disableInfo.isDisable()) {
			newsComment.setStatus(2);
			StringBuffer content=new StringBuffer();
			content.append(DisableInfo.DisableText);
			content.append("距离解封时间还有"+disableInfo.getDiffTime());
			newsComment.setContent(content.toString());
			return newsComment;
		}
		
		User user=userMapper.selectBaseData(userId);
		newsComment.setUser(user);
		newsComment.setId(null);//使用自动生成的主键
		newsComment.setTime(new Timestamp(System.currentTimeMillis()));
		if (newsComment.getLikecount()==null) {
			newsComment.setLikecount(0);
		}
		boolean notlegal=TextAntispamScanSample.scanText(newsComment.getContent());
		if (notlegal) {//如果不合法
			newsComment.setStatus(0);
			newsCommentService.insertBlockNewsComment(newsComment);
			////异步发送 socket 给管理员
			AdminMsg adminMsg=new AdminMsg(newsComment.getUserId(), newsComment.getContent(), newsComment.getTime(),KinfOf ,user);
			adminMsgSocketService.saveAndSendAdminMsgOfNotLegalComment(adminMsg);
			//异步检查 同一个资讯下非法评论是否 超过3 尝试发送模板消息给管理员
			newsCommentService.checkNotLegalCountAndSend(appId,newsComment.getNewsId());
			
		}else {
			newsComment.setStatus(1);
			newsCommentService.insertNewsComment(newsComment);
			//异步线程执行评论数+1
			newsService.addNewsCommentCount(newsComment.getNewsId());
		}
		
		return newsComment;
	}
	/**
	 * 点赞资讯评论
	 * @return
	 */
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int addNewsCommentLike(@RequestBody(required=false)NewsCommentLike newsComment ){
		return newsCommentService.addNewsCommentLike(newsComment);
	}
	
	
}


