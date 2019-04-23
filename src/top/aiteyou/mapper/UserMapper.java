package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.User;

/**
	*@author :tb
	*@version 2018年3月22日 上午11:27:05
*/
@Repository
public interface UserMapper {
	User selectByPrimaryKey(int id);
	
	User selectBaseData(int id);
	
	User selectUserIdentity(int id);
	
	int insert(User user);

	int updateBaseData(User user);

	int updateSessionKey(User user);

	String selectOpenid(@Param("userId")int userId);

	User seltctVipLevel(@Param("userId")Integer userId);

	int updateIdentity(User user);

	User selectUserBaseData(@Param("userId")Integer userId);

	List<User> selectAllAdminUser();

	List<User> selectAllUser(@Param("appId")int appId);

	List<User> selectGriefReplyer();

	
	
}


