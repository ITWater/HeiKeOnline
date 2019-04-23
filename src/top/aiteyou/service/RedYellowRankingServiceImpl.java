package top.aiteyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.RedYellowRanking;
import top.aiteyou.mapper.RedYellowRankingMapper;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:37:07
*/
@Service
public class RedYellowRankingServiceImpl implements RedYellowRankingService{
	
	@Autowired
	private RedYellowRankingMapper redYellowRankingMapper=null;
	
	@Cacheable(value="gameRedYellowRanking",key="'redyellow_game_' + #gameId")
	@Override
	public List<RedYellowRanking> select(int gameId) {
		return redYellowRankingMapper.selectAll(gameId);
	}

}


