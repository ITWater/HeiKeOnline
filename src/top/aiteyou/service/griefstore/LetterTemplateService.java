package top.aiteyou.service.griefstore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.User;
import top.aiteyou.entity.griefstore.Letter;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.service.TemplateInfoInformService;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
	*@author :tb
	*@version 2018年6月3日 下午5:34:47
*/
@Service
public class LetterTemplateService extends TemplateInfoInformService{
	
	private static final String LetterReplyTemplateId="HtDZlF2DcfnOljg4LIqzx5jYtjY8i5sc-7JBSS3oPA4";
	private static final String LetterReplyPage="pages/me/me";
	/**
	 * 解忧信回复 模板消息推送
	 */
	@Async
	public void sendGriefLetterReply(Letter letter){
		//评论者
		int userId=letter.getFromUserId();
		User replyer=userMapper.selectBaseData(userId);
		String replyerName=replyer.getName();
//		//回信内容
//		String replyContent=letter.getText();
		//接收推送人
		int acceptUserId=letter.getToUserId();
		
		
		TemplateMessage templateMessage=new TemplateMessage();
	
		templateMessage.setTemplate_id(LetterReplyTemplateId);
		templateMessage.setPage(LetterReplyPage);
		
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		
		map.put("keyword1", new TemplateMessageKeyWord("同学", "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(letter.getTime());
		map.put("keyword2", new TemplateMessageKeyWord(sendTime, "#173177"));
		map.put("keyword3", new TemplateMessageKeyWord("这有一封你的解忧回信", "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法
		try {
			prepareSendTemplateData(acceptUserId, 2, templateMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("推送解忧信回复时出错"+e.getMessage());
		}
	}

}


