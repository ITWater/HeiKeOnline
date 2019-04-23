package top.aiteyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.AppStatus;
import top.aiteyou.mapper.AppStatusMapper;

/**
	*@author :tb
	*@version 2018年5月9日 下午3:27:55
*/
@Service
public class AppStatuServie {
	@Autowired
	private AppStatusMapper appStatusMapper=null;
	
	@Cacheable(value="appStatus",key="'appId_'+#appId")
	public AppStatus selectAppStatus(Integer appId) {
		return appStatusMapper.selectAppStatus(appId);
	}

}


