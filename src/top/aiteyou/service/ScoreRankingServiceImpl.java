package top.aiteyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.ScoreRanking;
import top.aiteyou.mapper.ScoreRankingMapper;

/**
	*@author :tb
	*@version 2018年4月8日 上午9:43:22
*/
@Service
public class ScoreRankingServiceImpl implements ScoreRankingService{
	@Autowired
	private ScoreRankingMapper scoreRankingMapper=null;
	
	
	@Cacheable(value="gameScoreRanking",key="'score_liansai_game_' +#gameId")
	@Override
	public List<ScoreRanking> selectCirculation(int gameId) {
		return scoreRankingMapper.selectByGameIdNotGroup(gameId);
	}
	/**
	 * 按照gameId 、groupname != '-1' ，并将结果按照groupname分层
	 */
	@Cacheable(value="gameScoreRanking",key="'score_group_game'+#gameId")
	@Override
	public List<ScoreRanking> selectGroup(Integer gameId) {
		return scoreRankingMapper.selectByGameIdGroup(gameId);
	}

}


