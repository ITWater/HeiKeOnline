package top.aiteyou.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.SystemMsg;
import top.aiteyou.entity.User;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
 *  给用户发发送系统消息-模板消息方式
	*@author :tb
	*@version 2018年5月3日 下午8:22:36
*/
@Service
public class SystemMsgTemplateService extends TemplateInfoInformService{
	
	//系统消息模板相关
		
			private static final String SystemMsgTemplateId1="PYuLPnMUA8aRnVqPsT53Mav3LkKfAsRuOCxcX4K8pwk";
			private static final String SystemMsgTemplateId2="jQYNgc96jD8mqyPOAtQraosll4xtWrg2wPwCu0LNIUU";
			private static final String SystemMsgTemplateIdPage="pages/news/news";//小程序首页（资讯）
			public static final String ReplyerName="黑科Online运营组";
	/**
	 * 发送系统消息对全体用户  异步
	 * @param articleComment
	 */
	public List<String> sendSystemMsgToAll(int appId,SystemMsg systemMsg) {
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=SystemMsgTemplateId1;
		if (appId==2) {
			template_id_temp=SystemMsgTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage(SystemMsgTemplateIdPage);
		String content=systemMsg.getText();
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		map.put("keyword1", new TemplateMessageKeyWord(ReplyerName, "#173177"));
		map.put("keyword2", new TemplateMessageKeyWord(content, "##fe9801"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword3", new TemplateMessageKeyWord(sendTime, "#173177"));
		templateMessage.setData(map);
		List<String> result=new ArrayList<String>();
		List<User> users=userMapper.selectAllUser(appId);
		for (User user : users) {
			int userId=user.getId();
			//调用父类方法
			try {
				String result_items=prepareSendTemplateData(userId, appId, templateMessage);
				result.add(result_items);
			} catch (Exception e) {
				Logger.getLogger(getClass()).error("推送全体系统消息时出错_"+userId+e.getMessage());
			}
		}
		return result;
		
	}		
			
	/**
	 * 发送系统消息对用户  异步
	 * @param articleComment
	 */
	@Async
	public String sendSystemMsg(int appId,SystemMsg systemMsg) {
		//接收推送人
		int acceptUserId=systemMsg.getUserId();
		String result="文本内容为空";
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=SystemMsgTemplateId1;
		if (appId==2) {
			template_id_temp=SystemMsgTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage(SystemMsgTemplateIdPage);
		String content=systemMsg.getText();
		if (content==null || content.length() <=0) {//如果内容为空
			return result;
		}
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		map.put("keyword1", new TemplateMessageKeyWord(ReplyerName, "#173177"));
		map.put("keyword2", new TemplateMessageKeyWord(content, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword3", new TemplateMessageKeyWord(sendTime, "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法
		try {
			result=	prepareSendTemplateData(acceptUserId, appId, templateMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("推送系统消息时出错"+e.getMessage());
		}
		return result;
	}
}


