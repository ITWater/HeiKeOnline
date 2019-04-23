package top.aiteyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.ShooterRanking;
import top.aiteyou.mapper.ShooterRankingMapper;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:04:49
*/

@Service
public class ShooterRankingServiceImpl implements ShooterRankingService{
	
	@Autowired
	private ShooterRankingMapper shooterRankingMapper=null;
	
	@Cacheable(value="gameShooterRanking",key="'shooter_game_' + #gameId")
	@Override
	public List<ShooterRanking> select(int gameId) {
		return shooterRankingMapper.selectAll(gameId);
	} 
	
}


