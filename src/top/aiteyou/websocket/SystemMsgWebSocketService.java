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

import top.aiteyou.entity.SystemMsg;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.NotReadMsgArticleComment;
import top.aiteyou.transform.NotReadSystemMsg;

/**
	*@author :tb
	*@version 2018年5月1日 下午1:33:22
*/
@Service
public class SystemMsgWebSocketService {
	private static final  String KindOf="systemMsgs";
	@Autowired
	private RedisTemplate redisTemplate=null;
	@Autowired
	private UserMapper userMapper=null;
	private Logger logger=Logger.getLogger(getClass());
	/**
	 * 保存 和尝试发送 系统消息
	 */
	@Async
	public void saveAndSendSystemMsg(SystemMsg systemMsg){
		//接收推送人
		Integer acceptUserId=systemMsg.getUserId();
		String cacheKey="notRead_systemmsg_userId"+acceptUserId;
		//存入缓存 先弹出后选择存入
		List<SystemMsg> systemMsg_cache=(List<SystemMsg>) redisTemplate.opsForList().leftPop(cacheKey);
		if (systemMsg_cache==null) {
			systemMsg_cache=new ArrayList<SystemMsg>();
		}
		systemMsg_cache.add(systemMsg);
		
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(acceptUserId.toString())) {
			WebSocketSession user=WebSocketPushHandler.users.get(acceptUserId.toString());
			if (user.isOpen()) {
				NotReadSystemMsg notReadSystemMsg=new NotReadSystemMsg(KindOf,systemMsg_cache);
				String value=JSONObject.toJSONString(notReadSystemMsg);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return;
				} catch (IOException e) {
					logger.error("webSocket消息systemMsg发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, systemMsg_cache);//左边存
	}
	public void getAndSendNotReadSystemMsgToUser(String userId){
		String cacheKey="notRead_systemmsg_userId"+userId;
		List<SystemMsg> systemMsg_cache=(List<SystemMsg>) redisTemplate.opsForList().leftPop(cacheKey);
		if (systemMsg_cache==null) {
			return;
		}
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(userId)) {
			WebSocketSession user=WebSocketPushHandler.users.get(userId);
			if (user.isOpen()) {
				NotReadSystemMsg notReadMsg_system=new NotReadSystemMsg(KindOf,systemMsg_cache);
				String value=JSONObject.toJSONString(notReadMsg_system);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return ;
				} catch (IOException e) {
					logger.error("webSocket消息systemMsg发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, systemMsg_cache);
	}
	/**
	 * 异步 给所有用户发送 系统消息
	 * @param msg
	 */
	@Async
	public void saveAndSendSystemMsgToAllUser(int appId,SystemMsg msg) {
		//获得所有用户
		List<User> allUsers= userMapper.selectAllUser(appId);
		for (User user : allUsers) {
			try {
				Integer userId=user.getId();
				//接收推送人
				String cacheKey="systemMsg_allUser_UserId"+userId;
				//存入缓存 先弹出后选择存入
				List<SystemMsg> systemMsg_cache=(List<SystemMsg>) redisTemplate.opsForList().leftPop(cacheKey);
				if (systemMsg_cache==null) {
					systemMsg_cache=new ArrayList<SystemMsg>();
				}
				systemMsg_cache.add(msg);
				
				//判断用户是否在线  使用websocket发送
				if (WebSocketPushHandler.users.containsKey(userId.toString())) {
					WebSocketSession session=WebSocketPushHandler.users.get(userId.toString());
					if (session.isOpen()) {
						NotReadSystemMsg notReadSystemMsg=new NotReadSystemMsg(KindOf,systemMsg_cache);
						String value=JSONObject.toJSONString(notReadSystemMsg);
						WebSocketMessage<?> webSocketMessage=new TextMessage(value);
						try {
							session.sendMessage(webSocketMessage);
							return;
						} catch (IOException e) {
							logger.error("webSocket消息systemMsg发送失败"+e.getMessage());
						}
					}
				}
				redisTemplate.opsForList().leftPush(cacheKey, systemMsg_cache);//左边存
			} catch (Exception e) {
			}
		}
	}
	public void getAndSendSystemMsgOfAllUser(String userId){
		String cacheKey="systemMsg_allUser_UserId"+userId;
		List<SystemMsg> systemMsg_cache=(List<SystemMsg>) redisTemplate.opsForList().leftPop(cacheKey);
		if (systemMsg_cache==null) {
			return ;
		}
		//判断用户是否在线  使用websocket发送
		if (WebSocketPushHandler.users.containsKey(userId)) {
			WebSocketSession user=WebSocketPushHandler.users.get(userId);
			if (user.isOpen()) {
				NotReadSystemMsg notReadMsg_system=new NotReadSystemMsg(KindOf,systemMsg_cache);
				String value=JSONObject.toJSONString(notReadMsg_system);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					user.sendMessage(webSocketMessage);
					return ;
				} catch (IOException e) {
					logger.error("webSocket消息systemMsg_alluser发送失败"+e.getMessage());
				}
			}
		}
		redisTemplate.opsForList().leftPush(cacheKey, systemMsg_cache);
	}
}


