package top.aiteyou.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 握手
	*@author :tb
	*@version 2018年4月27日 上午10:19:21
*/

public class MyWebSocketInterceptor implements HandshakeInterceptor {
	 
	
	 /**
	  * 在handle前处理 解析参数
	  */
	  @Override
	  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2,
	      Map<String, Object> arg3) throws Exception {
	    // 将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息
	    if (request instanceof ServletServerHttpRequest) {
	      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
	      HttpServletRequest httpRequest = servletRequest.getServletRequest();
	      String userId= httpRequest.getParameter("userId");
	      arg3.put("userId", userId);
	    }
	 
	 
	    return true;
	  }
	 
	  @Override
	  public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
	    // TODO Auto-generated method stub
	 
	  }
	 
	}

