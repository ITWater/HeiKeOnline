package top.aiteyou.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;


/**
 * 	DisPathcerServlet 配置 spring mvc
	*@author :tb
	*@version 2018年3月18日 下午4:59:53
*/
@Configuration
//定义Spring MVC扫描的包
@ComponentScan(value="top.aiteyou.*", includeFilters= {@Filter(type = FilterType.ANNOTATION, value = Controller.class)})
//启动Spring MVC配置
@EnableWebMvc
@EnableAsync //当在spring环境中遇到@Async就会启动这个任务池一条线程去运行对应的方法
public class WebConfig extends AsyncConfigurerSupport {
	
	
	
	
	/**
	 * 获取一个任务池
	 * @return
	 */
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);//最小执行器数量
		taskExecutor.setMaxPoolSize(10);//最大~
		taskExecutor.setQueueCapacity(200);//任务队列容量
		taskExecutor.initialize();
		return taskExecutor;
	}
	/**
	 * 通过注解@Bean初始化视图解析器（jsp）
	 * @return viewResolver 视图解析器
	 */
	@Bean(name="internalResourceViewResolver")
	public ViewResolver initViewResolver(){
		InternalResourceViewResolver viewResolver=new 
				InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	@Bean(name = "multipartResolver")
	public MultipartResolver initMultipartResolver() {
		CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10*1024*1024);
		multipartResolver.setMaxInMemorySize(2*1024);//小于它不会产生临时文件
		return multipartResolver;
	}
	
	/**
	 * 初始化RequestMappingHandleAdapter，并加载http的json转换器
	 * @return
	 */
	public HandlerAdapter initRequestMappingHandleAdapter(){
		RequestMappingHandlerAdapter handlerAdapter=
				new RequestMappingHandlerAdapter();
		//准备json转换器
		MappingJackson2HttpMessageConverter jsonConverter=
				new MappingJackson2HttpMessageConverter();
			//加入转换器的支持类型
			List<MediaType> supportedMediaTypes=new ArrayList<MediaType>();
			supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
			jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
		//往适配器加入json转换器 这样当控制及其遇到注解@ReponseBody的时候就会采用Json消息类型应答
		handlerAdapter.getMessageConverters().add(jsonConverter);
		
		return handlerAdapter;
				
	}
	
}


