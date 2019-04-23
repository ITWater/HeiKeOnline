package top.aiteyou.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;

import top.aiteyou.transform.VODUploadAuthor;
import top.aiteyou.transform.VideoUploadInfo;
import top.aiteyou.vod.VODUtils;

/**
	*@author :tb
	*@version 2018年4月12日 下午12:46:50
*/
@Controller
@RequestMapping(value="/vod")
public class VideoController {
	
	/**
	 * 上传视频获取uploadAuth uploadAddress 、videoId
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value="/author",method=RequestMethod.POST)
	@ResponseBody
	public VODUploadAuthor authorization(@RequestBody VideoUploadInfo videoUploadInfo,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		VODUploadAuthor vodUploadAuthor=null;
		if (videoUploadInfo.getFileName()== null || "".equals(videoUploadInfo.getFileName())) {
			videoUploadInfo.setFileName("未命名");
		}
		if (videoUploadInfo.getTitle()== null || "".equals(videoUploadInfo.getTitle())) {
			videoUploadInfo.setTitle("未命名.mp4");
		}
		try {
			vodUploadAuthor=VODUtils.authorVODUpload(videoUploadInfo.getFileName(), videoUploadInfo.getTitle());
		} catch (ClientException e) {
			Logger.getLogger(getClass()).error("上传视频获取凭证失败"+e.getErrMsg());
		}
		return vodUploadAuthor;
	}
	
	//当网络异常导致文件上传失败时,可刷新上传凭证后再次执行上传操作
	@RequestMapping(value="/refreshauthor/{videoId}",method=RequestMethod.GET)
	@ResponseBody
	public VODUploadAuthor refreshUploadVideo(@PathVariable("videoId")String videoId,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		VODUploadAuthor vodUploadAuthor=null;
		if (videoId==null || "".equals(videoId)) {
		 return vodUploadAuthor;
		}
		try {
			vodUploadAuthor=VODUtils.refreshUploadVideoAuthor(videoId);
		} catch (ClientException e) {
			Logger.getLogger(getClass()).error("上传视频中断重新获取凭证失败"+e.getErrMsg());
		}
		return vodUploadAuthor;
		
	}
	
	
	/**
	 * 每次打开某视频都得获取视频的地址列表
	 */
	@RequestMapping(value="/videoinfo/{videoId}",method=RequestMethod.GET)
	@ResponseBody
	public Object getVideoInfo(@PathVariable("videoId") String videoId){
		String videoInfo=null;
		try {
			videoInfo=VODUtils.getVideoInfo(videoId);
		} catch (IOException e) {
			Logger.getLogger(getClass()).error("获取视频id"+videoId+"的播放地址列表时出错"+e.getMessage());
		}
		return JSON.parse(videoInfo);
	}
	

}


