package top.aiteyou.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.UserReallyInfo;

/**
	*@author :tb
	*@version 2018年5月26日 下午3:46:40
*/
@Repository
public interface UserReallyInfoMapper {
	int insert(UserReallyInfo userReallyInfo);

	void updateBindSuccess(@Param("userReallyInfoId")Integer userReallyInfoId, @Param("isBind")int b);

	UserReallyInfo selectByUserId(@Param("userId")Integer userId);
}


