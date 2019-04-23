package top.aiteyou.websocket;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
 /**
  * 管理员使用的socket处理器
  * @author tb
  * *@version 2018年4月27日 上午10:18:56
  */
public class AdminWebSocketPushHandler implements WebSocketHandler {
  // 用户进入系统监听 hashMap 方便查找
  public  static final HashMap<String,WebSocketSession> users = new HashMap<>();
  @Autowired
  private AdminMsgOfNotLegalCommentWebSocketService adminMsgOfNotLegalCommentWebSocketService=null;
  @Autowired
  private AdminMsgOfNewArticleWebSocketService adminMsgOfNewArticleWebSocketService=null;
  /**
   * 用户上线  尝试获取未读消息 和发送
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	  String userId=session.getAttributes().get("userId").toString();
	  users.put(userId,session);
	  //同步 发送未读的管理员消息(非法评论)
	  adminMsgOfNotLegalCommentWebSocketService.getAndSendAdminMsgOfNotLegalComment(session);
	  //同步 发送未读的管理员消息 (新动态消息)
	  adminMsgOfNewArticleWebSocketService.getAndSendAdminMsgOfNotLegalComment(session);
  }
 
  //处理客户端的消息
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
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
 

}