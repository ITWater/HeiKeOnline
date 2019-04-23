package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.ScoreRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午9:44:18
*/
@Repository
public interface ScoreRankingMapper {

	List<ScoreRanking> selectByGameIdNotGroup(@Param("gameId")int gameId);

	List<ScoreRanking> selectByGameIdGroup(@Param("gameId")Integer gameId);

}


