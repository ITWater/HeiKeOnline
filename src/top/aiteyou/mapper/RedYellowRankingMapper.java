package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.RedYellowRanking;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:38:41
*/
@Repository
public interface RedYellowRankingMapper {

	List<RedYellowRanking> selectAll(@Param("gameId")int gameId);

}


