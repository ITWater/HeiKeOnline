package top.aiteyou.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.FeedBack;
import top.aiteyou.entity.SystemMsg;
import top.aiteyou.mapper.FeedBackMapper;
import top.aiteyou.service.FeedBackTemplateMsgService;
import top.aiteyou.websocket.SystemMsgWebSocketService;

/**
	*@author :tb
	*@version 2018年4月2日 下午8:42:40
*/
@Controller
@RequestMapping(value="/feedbacks")
public class FeedBackController {
	@Autowired
	private FeedBackMapper feedBackMapper=null;
	@Autowired
	private FeedBackTemplateMsgService feedBackTemplateMsgService=null;
	@Autowired
	private SystemMsgWebSocketService systemMsgWebSocketService=null;
	/**
	 * 反馈
	 * @param feedBack
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public int addNewFeedBack(@RequestBody FeedBack feedBack,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		feedBack.setTime(new Timestamp(System.currentTimeMillis()));
		return feedBackMapper.insertSelective(feedBack);
	}
	/**
	 * 反馈回复
	 * @param feedBack
	 * @return
	 */
	@RequestMapping(value="/app{appId}/feedBackreply",method=RequestMethod.POST)
	@ResponseBody
	public int replyFeedBack(@PathVariable("appId")int appId,@RequestBody FeedBack feedBack){
		int result=feedBackMapper.replyFeedBack(feedBack);
		//异步通知用户----templateMsg
		feedBackTemplateMsgService.templateMsgInformUser(appId,feedBack);
		//异步 websocket
		Timestamp now=new Timestamp(System.currentTimeMillis());
		SystemMsg systemMsg=new SystemMsg(feedBack.getUserId(), feedBack.getReplyContent(),now);
		systemMsgWebSocketService.saveAndSendSystemMsg(systemMsg);
		
		return result;
	}
}


