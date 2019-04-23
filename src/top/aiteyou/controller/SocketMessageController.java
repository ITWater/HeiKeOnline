package top.aiteyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import top.aiteyou.websocket.WebSocketPushHandler;

/**
	*@author :tb
	*@version 2018年4月27日 上午11:02:38
*/
@Controller
@RequestMapping(value="/websocket")
public class SocketMessageController {
	
	@Autowired
	private WebSocketPushHandler webSocketHandler=null;
	
	@RequestMapping(value="/send/{msg}/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public int sendMsg(@PathVariable("msg") String msg,@PathVariable("userId")String userId){
//		webSocketHandler.sendMessageToUser(userId, new TextMessage(msg));
		return 1;
	}
}	


