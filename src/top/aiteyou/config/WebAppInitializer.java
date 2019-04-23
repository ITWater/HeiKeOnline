package top.aiteyou.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *  
	*@author :tb
	*@version 2018年3月18日 下午4:55:03
*/

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	
	
	//spring Ioc 环境配置
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{RootConfig.class};
	}
	
	//DispatcherServlet 环境配置
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{WebConfig.class};
	}
	
	//DispatcherServlet 拦截请求配置
	@Override
	protected String[] getServletMappings() {
		return new String[]{"*.do"};
	}
	
	//dynamic Servlet 上传文件配置
	/*@Override
	protected void customizeRegistration(Dynamic registration) {
		String filePath="d:/mvc/uploads";//文件存放地址
		Long singleMax=(long)(5*Math.pow(2, 20));//5M
		Long totalMax=(long)(20*Math.pow(2, 20));//5M
		registration.setMultipartConfig(new MultipartConfigElement(filePath, singleMax, totalMax, 0));
	}*/
	

}


