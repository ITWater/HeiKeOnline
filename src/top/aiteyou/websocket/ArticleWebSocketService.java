package top.aiteyou.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.schoolarticle.ArticleMapper;
import top.aiteyou.service.schoolarticle.ArticleCommentService;
import top.aiteyou.transform.NotReadMsgArticleComment;

/**
	*@author :tb
	*@version 2018年4月27日 下午6:52:27
*/
@Service
public class ArticleWebSocketService {
	private static final String KindOf="articleComments";
	
	
	@Autowired
	private ArticleMapper articleMapper=null;
	@Autowired
	private RedisTemplate redisTemplate=null;
	@Autowired
	private ArticleCommentService articleCommentService=null;
	
	private Logger logger=Logger.getLogger(getClass());
			
	/**
	 * 关于校园动态评论的消息  存入redis 和尝试发送
	 * @param articleComment
	 */
	@Async
	public  void saveAndSendArticleCommentMsg(ArticleComment articleComment){
		//接收推送人
		int articleId=articleComment.getArticleId();
		Article article= articleMapper.selectBaseData(articleId);
		Integer acceptUserId=article.getUserId();
		int articleCommentId=articleComment.getId();
		String cacheKey="notRead_schoolCommet_userId"+acceptUserId;
		//存入缓存 先弹出后选择存入
		List<ArticleComment> articleComments_cache=(List<ArticleComment>) redisTemplate.opsForList().leftPop(cacheKey);
		if (articleComments_cache==null) {
			articleComments_cache=new ArrayList<ArticleComment>();
		}
		articleComments_cache.add(articleComment);
		
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(acceptUserId.toString())) {
			WebSocketSession user=WebSocketPushHandler.users.get(acceptUserId.toString());
			if (user.isOpen()) {
				NotReadMsgArticleComment notReadMsg_Comment=new NotReadMsgArticleComment(KindOf,articleComments_cache);
				String value=JSONObject.toJSONString(notReadMsg_Comment);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return;
				} catch (IOException e) {
					logger.error("webSocket消息articleComment发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, articleComments_cache);//左边存
		
	}
	/**
	 * 关于校园动态评论的消息  从redis取 和尝试发送
	 * @param userId
	 */
	public void getAndSendArticleCommentMsg(String userId) {
		String cacheKey="notRead_schoolCommet_userId"+userId;
		List<ArticleComment> articleComments_cache=(List<ArticleComment>) redisTemplate.opsForList().leftPop(cacheKey);
		if (articleComments_cache==null) {
			return;
		}
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(userId)) {
			WebSocketSession user=WebSocketPushHandler.users.get(userId);
			if (user.isOpen()) {
				NotReadMsgArticleComment notReadMsg_Comment=new NotReadMsgArticleComment(KindOf,articleComments_cache);
				String value=JSONObject.toJSONString(notReadMsg_Comment);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return ;
				} catch (IOException e) {
					logger.error("webSocket消息articleComment发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, articleComments_cache);
	}
}


