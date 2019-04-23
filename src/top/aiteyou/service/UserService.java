package top.aiteyou.service;

import java.util.List;

import top.aiteyou.entity.User;

/**
	*@author :tb
	*@version 2018年4月1日 上午10:58:51
*/

public interface UserService {

	User getUserOpenIdSessionKey(int appId,String code) throws Exception;

	int updateSelective(User user);

	User registerUser(int appId,String code) throws Exception;

	User updateSessionKey(int appId,Integer id, String code) throws Exception;

	User getUserBaseData(Integer userId);
	
	List<User> getAllAdminUser();

}


