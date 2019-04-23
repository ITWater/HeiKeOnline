package top.aiteyou.service;

import java.util.List;

import top.aiteyou.entity.ScoreRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午9:42:27
*/

public interface ScoreRankingService {
	List<ScoreRanking> selectCirculation(int gameId);

	List<ScoreRanking> selectGroup(Integer gameId);
}


