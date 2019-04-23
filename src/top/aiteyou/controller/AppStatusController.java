package top.aiteyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.AppStatus;
import top.aiteyou.service.AppStatuServie;

/**
	*@author :tb
	*@version 2018年5月9日 下午3:22:13
*/
@Controller
@RequestMapping(value="/appstatus")
public class AppStatusController {
	@Autowired
	private AppStatuServie appStatusService=null;
	
	/**
	 * 获取某个小程序的运行状态
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="/{appId}",method=RequestMethod.GET)
	@ResponseBody
	public AppStatus getAppStatus(@PathVariable("appId") Integer appId ){
	  return appStatusService.selectAppStatus(appId);
  }
}


