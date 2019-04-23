package top.aiteyou.service.schoolarticle;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.FormValue;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.mapper.schoolarticle.ArticleMapper;
import top.aiteyou.service.TemplateInfoInformService;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
	*@author :tb
	*@version 2018年4月20日 下午4:09:05
*/
@Service
public class SchoolArticleTemplateMsgService extends TemplateInfoInformService{
	@Autowired
	private ArticleMapper articleMapper=null;
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	
	//评论回复模板相关
			//动态评论回复
			private static final String SchoolCommentReplyTemplateId1="VZ3ZP_4T_mTYREPW2ogXQWDMCDFbrl1iWj08rMhQFaA";
			private static final String SchoolCommentReplyTemplateId2="S3q6v89kjaZfz08NKsA6UDxPRdXWKv_-W-Rk2kzcoXQ";
			private static final String SchoolArticleDetailPage="pages/tools/tools-game-today/tools-game-today";
			//动态评论 评论回复
			private static final String SchoolCommentToCommentReplyTemplateId1="VZ3ZP_4T_mTYREPW2ogXQWDMCDFbrl1iWj08rMhQFaA";
			private static final String SchoolCommentToCommentReplyTemplateId2="S3q6v89kjaZfz08NKsA6UDxPRdXWKv_-W-Rk2kzcoXQ";
			private static final String SchoolArticleCommentDetailPage="pages/tools/tools-game-today/tools-game-today";
	/**
	 * 对校园动态  	评论回复通知  异步
	 * @param articleComment
	 */
	@Async
	public void schoolArticleCommentReply(int appId,ArticleComment articleComment) {
		//评论者
		int userId=articleComment.getUserId();
		User replyer=userMapper.selectBaseData(userId);
		String replyerName=replyer.getName();
		//评论内容
		String replyContent=articleComment.getText();
		//接收推送人
		int articleId=articleComment.getArticleId();
		Article article= articleMapper.selectBaseData(articleId);
		int acceptUserId=article.getUserId();
		
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=SchoolCommentReplyTemplateId1;
		if (appId==2) {
			template_id_temp=SchoolCommentReplyTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage(SchoolArticleDetailPage);
		
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		//截取问题回复内容  截取长度30
		if (replyContent.length()>30) {
			replyContent=replyContent.substring(0, 30)+"...";
		}
		map.put("keyword1", new TemplateMessageKeyWord(replyerName, "#173177"));
		map.put("keyword2", new TemplateMessageKeyWord(replyContent, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword3", new TemplateMessageKeyWord(sendTime, "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法
		try {
			prepareSendTemplateData(acceptUserId, appId, templateMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("推送校园动态评论回复时出错"+e.getMessage());
		}
	}
	/**
	 * 对校园动态评论  评论回复通知  异步
	 * @param articleCommentToComment
	 */
	@Async
	public void schoolArticleCommentToCommentReply(int appId,ArticleCommentToComment commentToComment) {
		//评论者
		int userId=commentToComment.getUserId();
		User replyer=userMapper.selectBaseData(userId);
		String replyerName=replyer.getName();
		//评论内容
		String replyContent=commentToComment.getContent();
		//接收推送人
		int articleCommentId=commentToComment.getCommentId();
		ArticleComment articleComment=articleCommentMapper.selectBaseData(articleCommentId);
		int acceptUserId=articleComment.getUserId();
		
		TemplateMessage templateMessage=new TemplateMessage();
		String template_id_temp=SchoolCommentToCommentReplyTemplateId1;
		if (appId==2) {
			template_id_temp=SchoolCommentToCommentReplyTemplateId2;
		}
		templateMessage.setTemplate_id(template_id_temp);
		templateMessage.setPage(SchoolArticleCommentDetailPage);
		
		Map<String,TemplateMessageKeyWord> map=new HashMap<String, TemplateMessageKeyWord>();
		//截取问题回复内容  截取长度30
		if (replyContent.length()>30) {
			replyContent=replyContent.substring(0, 30)+"...";
		}
		map.put("keyword1", new TemplateMessageKeyWord(replyerName, "#173177"));
		map.put("keyword2", new TemplateMessageKeyWord(replyContent, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword3", new TemplateMessageKeyWord(sendTime, "#173177"));
		templateMessage.setData(map);
		
		//调用父类方法
		try {
			prepareSendTemplateData(acceptUserId, appId, templateMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("推送校园动态评论的评论回复时出错"+e.getMessage());
		}
	}
}


