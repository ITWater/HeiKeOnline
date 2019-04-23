package top.aiteyou.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.SystemMsg;
import top.aiteyou.service.SystemMsgTemplateService;
import top.aiteyou.websocket.SystemMsgWebSocketService;

/**
 * 系统消息
	*@author :tb
	*@version 2018年5月3日 下午7:49:05
*/
@Controller
@RequestMapping(value="/systemmsg") 
public class SystemMessageController {
	@Autowired
	private SystemMsgWebSocketService systemMsgWebSocketService=null;
	@Autowired
	private SystemMsgTemplateService systemMsgTemplateService=null;
	/**
	 * 全体通知 分平台
	 * @param appId
	 * @param systemMsg
	 * @return
	 */
	@RequestMapping(value="/app{appId}",method=RequestMethod.POST)
	@ResponseBody
	public String infoUser(@PathVariable("appId")int appId,@RequestBody SystemMsg systemMsg){
		systemMsg.setText(systemMsg.getText().replace("\\r", "\r"));
		systemMsg.setText(systemMsg.getText().replace("\\n", "\n"));
		systemMsg.setTime(new Timestamp(System.currentTimeMillis()));
		//异步 存储和尝试发送系统消息
		systemMsgWebSocketService.saveAndSendSystemMsg(systemMsg);
		//判断消息的重要程度
		Integer importantLevel=systemMsg.getImportantLevel();
		String result="消息重要等级低于模板消息，不发送模板消息";
		if (importantLevel<=5) {
			//异步 模板消息
			result=systemMsgTemplateService.sendSystemMsg(appId,systemMsg);
		}
			//异步 活动页面 （之后考虑做）
		return result;
	}
	@RequestMapping(value="/app{appId}/toalluser",method=RequestMethod.POST)
	@ResponseBody
	public List<String> sendAllUserSocketSystemMsg(@PathVariable("appId")int appId,@RequestBody SystemMsg msg){
		msg.setText(msg.getText().replace("\\r", "\r"));
		msg.setText(msg.getText().replace("\\n", "\n"));
		List<String> result=null;
		msg.setTime(new Timestamp(System.currentTimeMillis()));
		//如果系统消息内容为空 退出
		if (msg.getText()==null || msg.getText().trim().equals("")) {
			return result;
		}
		//异步 存储和尝试发送系统消息 --socket
		systemMsgWebSocketService.saveAndSendSystemMsgToAllUser(appId,msg);
		if (msg.getImportantLevel()==1) {
			//异步 存储和尝试发送全体模板消息 重大事件时用
			 result=systemMsgTemplateService.sendSystemMsgToAll(appId,msg);
		}
		return result;
	}
	
}


