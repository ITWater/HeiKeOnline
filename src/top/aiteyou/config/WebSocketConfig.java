package top.aiteyou.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import top.aiteyou.websocket.AdminWebSocketPushHandler;
import top.aiteyou.websocket.MyWebSocketInterceptor;
import top.aiteyou.websocket.WebSocketPushHandler;

/**
 * websocket 配置
	*@author :tb
	*@version 2018年4月27日 上午8:19:02
*/
@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer  {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(WebSocketPushHandler(), "/socket/webSocketServer.do")
		 	.addInterceptors(new MyWebSocketInterceptor()).setAllowedOrigins("*");
	    registry.addHandler(WebSocketPushHandler(), "/sockjs/webSocketServer.do")
	        .addInterceptors(new MyWebSocketInterceptor()).withSockJS();
	   
	    registry.addHandler(AdminWebSocketPushHandler(), "/socket/adminSocketServer.do")
	   	    .addInterceptors(new MyWebSocketInterceptor()).setAllowedOrigins("*");
	    registry.addHandler(AdminWebSocketPushHandler(), "/sockjs/adminSocketServer.do")
        .addInterceptors(new MyWebSocketInterceptor()).withSockJS();
	}
	@Bean(name="webSocketHandler")
	  public WebSocketPushHandler WebSocketPushHandler() {
	    return new WebSocketPushHandler();
	  }
	@Bean(name="adminWebSocketService")
	public AdminWebSocketPushHandler AdminWebSocketPushHandler(){
		return new AdminWebSocketPushHandler();
	}
	
}


