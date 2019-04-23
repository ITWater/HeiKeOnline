package top.aiteyou.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.oss.CalculateOSSUploadAuthor;
import top.aiteyou.oss.OSSConfig;
import top.aiteyou.transform.OSSUploadAuthor;

/**
 *  oss 上传授权
 * 	*@author :tb
 * 	*@version 2018年4月12日 上午11:12:13
*/
@Controller
@RequestMapping(value="/oss")
public class OssUploadController {
	private static OSSConfig ossConfig=OSSConfig.getInstance();
	
	@RequestMapping(value="/author/{dir}",method=RequestMethod.GET)
	@ResponseBody
	public OSSUploadAuthor authorization(@PathVariable("dir")String dir,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (dir== null  || "".equals(dir)) {
			dir="template";
		}
		dir=dir+"/";
		OSSUploadAuthor ossUploadAuthor=null;
		try {
			ossUploadAuthor=CalculateOSSUploadAuthor.calculate(ossConfig, dir);
			ossUploadAuthor.setHost("https://static.kexie.group");
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(getClass()).error("计算上传oss签名失败"+e.toString());
		}
		return ossUploadAuthor;
	}
}


