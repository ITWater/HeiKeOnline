package top.aiteyou.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.AppStatus;

/**
	*@author :tb
	*@version 2018年5月9日 下午3:30:20
*/
@Repository
public interface AppStatusMapper {

	AppStatus selectAppStatus(@Param("appId")Integer appId);
	
}


