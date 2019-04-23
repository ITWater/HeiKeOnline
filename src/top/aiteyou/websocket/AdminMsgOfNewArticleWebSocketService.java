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

import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.service.UserService;
import top.aiteyou.transform.NotReadAdminMsg;

/**
 * 给后台管理系统 推送socket消息
	*@author :tb
	*@version 2018年5月4日 下午7:43:51
*/
@Service
public class AdminMsgOfNewArticleWebSocketService {
	@Autowired
	private UserService userService=null;
	@Autowired
	private RedisTemplate redisTemplate=null;
	@Autowired
	private UserMapper userMapper=null;
	private Logger logger=Logger.getLogger(getClass());
	
	private static final String KindOf="NewArticles";
	
	/**
	 * 保存 和尝试发送 管理员消息 对第一个登录
	 */
	@Async
	public void saveAndSendAdminMsgOfNotLegalComment(AdminMsg adminMsg){
		//消息关于的用户
		 User user=userMapper.selectBaseData(adminMsg.getUserId());
		 adminMsg.setUser(user);
		String cacheKey="notRead_adminMsg_newArticles";
		//存入缓存 先弹出后选择存入
		List<AdminMsg> adminMsg_cache=(List<AdminMsg>) redisTemplate.opsForList().leftPop(cacheKey);
		if (adminMsg_cache==null) {
			adminMsg_cache=new ArrayList<
					>();
		}
		adminMsg_cache.add(adminMsg);
		
		//判断管理员是否在线  使用websocket发送  hashmap 中的第一个
		for (WebSocketSession adminSession : AdminWebSocketPushHandler.users.values()) {
			if (adminSession.isOpen()) {
				NotReadAdminMsg notReadAdminMsg=new NotReadAdminMsg(KindOf, adminMsg_cache); 
				String value=JSONObject.toJSONString(notReadAdminMsg);
				WebSocketMessage<?> webSocketMessage=new TextMessage(value);
				try {
					adminSession.sendMessage(webSocketMessage);
					return;
				} catch (IOException e) {
					logger.error("webSocket消息adminMsg_newarticles发送失败"+e.getMessage());
				}
			}
		}
		//如果所有的管理员都不在线
		redisTemplate.opsForList().leftPush(cacheKey, adminMsg_cache);//左边存
	}
	/**
	 * 某管理员获取 尝试发送未读的管理员消息(关于新发表的动态)
	 * @param admin
	 */
	public void getAndSendAdminMsgOfNotLegalComment(WebSocketSession admin){
		String cacheKey="notRead_adminMsg_newArticles";
		List<AdminMsg> adminMsg_cache=(List<AdminMsg>) redisTemplate.opsForList().leftPop(cacheKey);
		if (adminMsg_cache==null) {
			adminMsg_cache=new ArrayList<AdminMsg>();
		}
		//判断用户是否在线  使用websocket发送
		if (admin.isOpen()) {
			NotReadAdminMsg notReadAdminMsg=new NotReadAdminMsg(KindOf, adminMsg_cache); 
			String value=JSONObject.toJSONString(notReadAdminMsg);
			WebSocketMessage<?> webSocketMessage=new TextMessage(value);
			try {
				admin.sendMessage(webSocketMessage);
				return ;
			} catch (IOException e) {
				logger.error("webSocket消息adminMsg_newarticles发送失败"+e.getMessage());
			}
		}
		//放回去
		redisTemplate.opsForList().leftPush(cacheKey, adminMsg_cache);
	}
}


