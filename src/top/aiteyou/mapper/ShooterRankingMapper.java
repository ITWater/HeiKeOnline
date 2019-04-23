package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.ShooterRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:06:17
*/
@Repository
public interface ShooterRankingMapper {

	List<ShooterRanking> selectAll(@Param("gameId")int gameId);

}


