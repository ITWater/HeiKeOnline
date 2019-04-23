package top.aiteyou.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.DisableSendMsg;

/**
	*@author :tb
	*@version 2018年5月6日 下午2:03:24
*/
@Repository
public interface DisableSendMsgMapper {
	public DisableSendMsg selectOneUser(@Param("userId")Integer userId);
}


