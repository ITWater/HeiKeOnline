package top.aiteyou.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

import top.aiteyou.entity.User;
import top.aiteyou.entity.griefstore.Letter;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.griefstore.NotReadLetter;

/**
	*@author :tb
	*@version 2018年5月3日 上午10:29:57
*/
@Service
public class LetterWebSocketService {
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private RedisTemplate redisTemplate=null;
	
	private static final String KindOf="griefStoreLetter";
	
	/**
	 * 异步  存储 和尝试发送信 给某人
	 * @param letter
	 */
	@Async
	public void saveAndSendUserOfLetter(Letter letter) {
		//绑定寄信人
			User sender=userMapper.selectBaseData(letter.getFromUserId());
			letter.setAuthor(sender);
			String accepterId=letter.getToUserId().toString();
			String cacheKey="notRead_griefstore_userId"+accepterId;
		//将邮件消息放进redis
			List<Letter> userLetters=(List<Letter>) redisTemplate.opsForList().leftPop(cacheKey);
			if (userLetters==null) {
				userLetters=new ArrayList<Letter>();
			}
			userLetters.add(letter);
		//若用户在线则发送 
			if (WebSocketPushHandler.users.containsKey(accepterId)) {
				WebSocketSession replyerSession=WebSocketPushHandler.users.get(accepterId);
				if (replyerSession.isOpen()) {
					NotReadLetter notReadLetter=new NotReadLetter(KindOf,userLetters);
					String obj_str=JSONObject.toJSONString(notReadLetter);
					try {
						replyerSession.sendMessage(new TextMessage(obj_str));
						return ;
					} catch (IOException e) {
						Logger.getLogger(getClass()).error("回复信发送失败"+e.getMessage());
					}
				}
			}
				redisTemplate.opsForList().leftPush(cacheKey, userLetters);
	}
	
	/**
	 * 获取未读的信
	 * @param userId
	 */
	public void getAndSendLetterToUser(String userId){
		String cacheKey="notRead_griefstore_userId"+userId;
		List<Letter> userLetters=(List<Letter>) redisTemplate.opsForList().leftPop(cacheKey);
		if (userLetters==null|| userLetters.size()==0) {
			return ;
		}
		//若用户在线则发送
			if (WebSocketPushHandler.users.containsKey(userId)) {
				WebSocketSession replyerSession=WebSocketPushHandler.users.get(userId);
				if (replyerSession.isOpen()) {
					NotReadLetter notReadLetter=new NotReadLetter(KindOf,userLetters);
					String obj_str=JSONObject.toJSONString(notReadLetter);
					try {
						replyerSession.sendMessage(new TextMessage(obj_str));
						return ;
					} catch (IOException e) {
						Logger.getLogger(getClass()).error("获取未读信失败"+e.getMessage());
					}
				}
			}
			redisTemplate.opsForList().leftPush(cacheKey, userLetters);
	}
	/**
	 * 随机选择解忧人 并发送websocket消息 
	 * @param userLetter
	 */
	@Async
	public void saveAndSendRandomUserOfLetter(Letter userLetter){
	//绑定寄信人
		User sender=userMapper.selectBaseData(userLetter.getFromUserId());
		userLetter.setAuthor(sender);
	//随机选择解忧人
		List<User> replyers=userMapper.selectGriefReplyer();
		if (replyers.size()==0) {//若没有解忧人
			return ;
		}
		Random random=new Random();
		int index=random.nextInt(replyers.size());
		User replyer=replyers.get(index);
		String replyerId=replyer.getId().toString();
		String cacheKey="notRead_griefstore_userId"+replyerId;
	//将邮件消息放进redis
		List<Letter> userLetters=(List<Letter>) redisTemplate.opsForList().leftPop(cacheKey);
		if (userLetters==null) {
			userLetters=new ArrayList<Letter>();
		}
		userLetters.add(userLetter);
	//若用户在线则发送 
		if (WebSocketPushHandler.users.containsKey(replyerId)) {
			WebSocketSession replyerSession=WebSocketPushHandler.users.get(replyerId);
			if (replyerSession.isOpen()) {
				NotReadLetter notReadLetter=new NotReadLetter(KindOf,userLetters);
				String obj_str=JSONObject.toJSONString(notReadLetter);
				try {
					replyerSession.sendMessage(new TextMessage(obj_str));
					return ;
				} catch (IOException e) {
					Logger.getLogger(getClass()).error("给解忧人发送烦恼信失败"+e.getMessage());
				}
			}
		}
			redisTemplate.opsForList().leftPush(cacheKey, userLetters);
	}
	
}


