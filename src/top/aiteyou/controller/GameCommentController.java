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
import top.aiteyou.entity.GameComment;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.service.GameCommentService;
import top.aiteyou.service.JudgeUserIsOnDisableSendMsgService;
import top.aiteyou.textscan.TextAntispamScanSample;
import top.aiteyou.transform.DisableInfo;
import top.aiteyou.transform.GameCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;

/**
 *  比赛评论接口
	*@author :tb
	*@version 2018年3月18日 下午6:50:50
*/

@Controller
@RequestMapping("/gamecomments")
public class GameCommentController {
	@Autowired
	private GameCommentService gameCommentService=null;
	@Autowired
	private AdminMsgOfNotLegalCommentWebSocketService adminMsgSocketService=null;
	private static final String KindOf="GameComment";
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private JudgeUserIsOnDisableSendMsgService judgeUserIsDisableSendMsg=null;
	/**
	 * 赛事评论添加
	 * @param appId
	 * @param gameComment
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value="/app{appId}",method=RequestMethod.POST)
	@ResponseBody
	public GameComment insertGameComment(@PathVariable("appId")int appId,@RequestBody GameComment gameComment) throws InterruptedException{
		int userId=gameComment.getUserId();
		// 如果用户禁言中
		DisableInfo disableInfo=judgeUserIsDisableSendMsg.judgeUserIsDisableSendMsg(userId);
		if (disableInfo.isDisable()) {
			gameComment.setStatus(2);
			StringBuffer content=new StringBuffer();
			content.append(DisableInfo.DisableText);
			content.append("距离解封时间还有"+disableInfo.getDiffTime());
			gameComment.setContent(content.toString());
			return gameComment;
		}
		
		User user=userMapper.selectBaseData(userId);
		gameComment.setUser(user);
		gameComment.setId(null);//使用自动生成的主键
		gameComment.setTime(new Timestamp(System.currentTimeMillis()));
		if (gameComment.getLikecount()==null) {
			gameComment.setLikecount(0);
		}
		boolean notlegal=TextAntispamScanSample.scanText(gameComment.getContent());
		if (notlegal) {//如果不合法
			gameComment.setStatus(0);
			gameCommentService.insertBlockNewsComment(gameComment);
			
			//异步发送 socket 给管理员
			AdminMsg adminMsg=new AdminMsg(gameComment.getUserId(), gameComment.getContent(), gameComment.getTime(),KindOf ,user);
			adminMsgSocketService.saveAndSendAdminMsgOfNotLegalComment(adminMsg);
			//异步检查 同一个赛事下非法评论是否 超过3  尝试发送模板消息给管理员
			gameCommentService.checkNotLegalCountAndSend(appId,gameComment.getGameId());
		}else {
			gameComment.setStatus(1);
			gameCommentService.insertGameCommentJoinUser(gameComment);
		}
		return gameComment;
	}
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int addGameCommentLikeCount(@RequestBody GameCommentLike gameCommentlike){
		return gameCommentService.addGameCommentLikeCount(gameCommentlike);
	}
	
}


