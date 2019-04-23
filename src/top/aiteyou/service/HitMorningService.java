package top.aiteyou.service;

import java.util.List;

import top.aiteyou.transform.HitMorningContext;
import top.aiteyou.transform.UserSign;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:23:36
*/

public interface HitMorningService {

	HitMorningContext morningSignIn(int userId);

	List<UserSign> getRanking();

}


