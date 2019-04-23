package top.aiteyou.websocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
 /**
  * 用户使用的socket处理器
  * @author tb
  * *@version 2018年4月27日 上午10:18:56
  */
public class WebSocketPushHandler implements WebSocketHandler {
  // 用户进入系统监听 hashMap 方便查找
  public  static final HashMap<String,WebSocketSession> users = new HashMap<>();
  @Autowired
  private ArticleWebSocketService articleWebService=null;
  @Autowired
  private SystemMsgWebSocketService systemMsgWebSocketService=null;
  @Autowired
  private ArticleCommentWebSocketService articleCommentWebService=null;
  @Autowired
  private LetterWebSocketService letterWebSocketService=null;
  /**
   * 用户上线  尝试获取未读消息 和发送
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	  String userId=session.getAttributes().get("userId").toString();
	  users.put(userId,session);
	  
	  //发送未读的系统消息-对所有用户的 -公告。。
	  systemMsgWebSocketService.getAndSendSystemMsgOfAllUser(userId);
	  //发送未读的系统消息-对个人的
	  systemMsgWebSocketService.getAndSendNotReadSystemMsgToUser(userId);
	  //发送未读的校园评论信息
	  articleWebService.getAndSendArticleCommentMsg(userId);
	  //发送未读的校园动态评论的评论消息
	  articleCommentWebService.getAndSendArticleCommentToCommentToUser(userId); 
	  //发送邮件
	  letterWebSocketService.getAndSendLetterToUser(userId);
  }
 
  //处理客户端的消息
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    // 我这儿并没有做处理，消息的封装格式一般有{from:xxxx,to:xxxxx,msg:xxxxx}，来自哪里，发送给谁，什么消息等等
    // TextMessage msg = (TextMessage)message.getPayload();
    // 给所有用户群发消息
    //sendMessagesToUsers(msg);
    // 给指定用户群发消息
    //sendMessageToUser(userId, msg);
	  System.out.println(message.toString());
  }
 
  // 后台错误信息处理方法
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
 
  }
 
  // 用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
	  String userId=session.getAttributes().get("userId").toString();
	  users.remove(userId); 
  }
 
  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
 
//  /**
//   * 给所有的用户发送消息
//   */
//  public void sendMessagesToUsers(TextMessage message) {
//    for (WebSocketSession user : users) {
//      try {
//        // isOpen()在线就发送
//        if (user.isOpen()) {
//          user.sendMessage(message);
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
 
  /**
   * 发送消息给指定的用户
 * @throws IOException 
   */
//  public void sendMessageToUser(String userId, TextMessage message) throws IOException {
//    for (WebSocketSession user : users) {
//      if (user.getAttributes().get("userId").equals(userId)) {
//          if (user.isOpen()) {
//            user.sendMessage(message);
//          }else{
//        	  users.remove(user);
//          }
//      }
//    }
//  }

}