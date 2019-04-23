package top.aiteyou.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.SystemMsg;
import top.aiteyou.entity.User;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
 * 
 * 管理员模板消息
	*@author :tb
	*@version 2018年5月4日 下午9:48:18
*/
@Service
public class AdminMsgTemplateService extends TemplateInfoInformService{
	@Autowired
	private UserService userService=null;
	
	//系统消息模板相关										
		private static final String AdminMsgTemplateId1="j0KI10rxAiMlyRHAlqd-pqsPDl-2PQGbJkAz7NRWsNI";
		private static final String AdminMsgTemplateId2="Z5_hsqCPJ8nRX3lR9pkmwF8IdHGMTsGfVpmc-VlYY6U";
		private static final String SystemMsgTemplateIdPage="news/news";
		public static final String ReplyerName="黑科Online智能大管家(:";
	/**
	 * 对黑科Online管理员推送模板消息 异步
	 * @param articleComment
	 */
	@Async
	public void sendAdminMsg(int appId,AdminMsg adminMsg) {
		//选择推送人 --- 所有Admin
		List<User> admins=userService.getAllAdminUser();
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=AdminMsgTemplateId1;
		if (appId==2) {
			template_id_temp=AdminMsgTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage(SystemMsgTemplateIdPage);
		String content=adminMsg.getContent();
		if (content==null || content.length() <=0) {//如果内容为空
			return ;
		}
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		map.put("keyword1", new TemplateMessageKeyWord(ReplyerName, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword2", new TemplateMessageKeyWord(sendTime, "#173177"));
		map.put("keyword3", new TemplateMessageKeyWord(content, "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法  循环发送
		for (User admin: admins) {
			try {
				prepareSendTemplateData(admin.getId(), appId, templateMessage);
			} catch (Exception e) {
				Logger.getLogger(getClass()).error("推送系统消息时出错"+e.getMessage());
			}
		}
	}
}


