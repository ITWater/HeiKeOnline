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
import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.transform.NotReadMsgArticleComment;
import top.aiteyou.transform.schoolarticle.NotReadMsgArticleCommentToComment;

/**
	*@author :tb
	*@version 2018年5月6日 上午10:39:14
*/
@Service
public class ArticleCommentWebSocketService {
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	@Autowired
	private RedisTemplate redisTemplate=null;
	
	private Logger logger=Logger.getLogger(getClass());
	
	private static final String KindOf="articleCommentToComments";
	/**
	 * 保存和尝试发送  动态评论的评论消息 通过socket
	 */
	@Async
	public void saveAndSendArticleCommentToCommentMsg(ArticleCommentToComment commentToComment){
		//接收推送人
		int articleId=commentToComment.getCommentId();
		ArticleComment articleComment= articleCommentMapper.selectBaseData(articleId);
		Integer acceptUserId=articleComment.getUserId();
		String cacheKey="notRead_schoolCommetToComment_userId"+acceptUserId;
		//存入缓存 先弹出后选择存入
		List<ArticleCommentToComment> articleCommentToComments_cache=(List<ArticleCommentToComment>) redisTemplate.opsForList().leftPop(cacheKey);
		if (articleCommentToComments_cache==null) {
			articleCommentToComments_cache=new ArrayList<ArticleCommentToComment>();
		}
		articleCommentToComments_cache.add(commentToComment);
		
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(acceptUserId.toString())) {
			WebSocketSession user=WebSocketPushHandler.users.get(acceptUserId.toString());
			if (user.isOpen()) {
				NotReadMsgArticleCommentToComment notReadMsg_CommentToComment=new NotReadMsgArticleCommentToComment(KindOf,articleCommentToComments_cache);
				String value=JSONObject.toJSONString(notReadMsg_CommentToComment);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return;
				} catch (IOException e) {
					logger.error("webSocket消息articleCommentToComment发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, articleCommentToComments_cache);//左边存
		
	}
	
	public void getAndSendArticleCommentToCommentToUser(String userId){
		String cacheKey="notRead_schoolCommetToComment_userId"+userId;
		List<ArticleCommentToComment> articleCommentToComments_cache=(List<ArticleCommentToComment>) redisTemplate.opsForList().leftPop(cacheKey);
		if (articleCommentToComments_cache==null) {
			return;
		}
		
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(userId)) {
			WebSocketSession user=WebSocketPushHandler.users.get(userId);
			if (user.isOpen()) {
				NotReadMsgArticleCommentToComment notReadMsg_CommentToComment=new NotReadMsgArticleCommentToComment(KindOf,articleCommentToComments_cache);
				String value=JSONObject.toJSONString(notReadMsg_CommentToComment);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return ;
				} catch (IOException e) {
					logger.error("webSocket消息articleCommentToComment发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, articleCommentToComments_cache);
	}
}


