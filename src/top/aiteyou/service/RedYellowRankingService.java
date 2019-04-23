package top.aiteyou.service;

import java.util.List;

import top.aiteyou.entity.RedYellowRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:35:42
*/

public interface RedYellowRankingService {

	List<RedYellowRanking> select(int gameId);

}


