package top.aiteyou.service;

import java.util.List;

import top.aiteyou.entity.ShooterRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:03:59
*/

public interface ShooterRankingService {

	List<ShooterRanking> select(int gameId);

}


