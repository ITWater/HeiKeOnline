package top.aiteyou.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.FeedBack;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;


/**
	*@author :tb
	*@version 2018年4月21日 下午6:11:30
*/
@Service
public class FeedBackTemplateMsgService extends TemplateInfoInformService{
	public static final String ReplyerName="黑科Online运营组";
	//反馈回复模板相关
		private static final String FeedBackReplyTemplateId1="j0KI10rxAiMlyRHAlqd-pqsPDl-2PQGbJkAz7NRWsNI";
		private static final String FeedBackReplyTemplateId2="GETliKQSV9O_ZyHYaOtj2gP2fJLkbuSJN7xowHLr7-A";
		//没有跳转页
	/**
	 * 异步通知
	 */
	@Async
	public void templateMsgInformUser(int appId,FeedBack feedBack){
		//反馈者
		String replyerName=ReplyerName;
		//评论内容
		String replyContent=feedBack.getReplyContent();
		//接收推送人
		int acceptUserId=feedBack.getUserId();
		
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=FeedBackReplyTemplateId1;
		if (appId==2) {
			template_id_temp=FeedBackReplyTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage("news/news");//无跳转页
		
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		
		map.put("keyword1", new TemplateMessageKeyWord(replyerName, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword2", new TemplateMessageKeyWord(sendTime, "#173177"));
		map.put("keyword3", new TemplateMessageKeyWord(replyContent, "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法
		try {
			prepareSendTemplateData(acceptUserId, appId, templateMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("推送反馈时出错"+e.getMessage());
		}
	}
}


