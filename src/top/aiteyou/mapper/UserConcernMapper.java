package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.UserConcern;

/**
	*@author :tb
	*@version 2018年4月2日 上午11:38:14
*/
@Repository
public interface UserConcernMapper {
	int insert(UserConcern userConcern);
	int delete(UserConcern userConcern);
	
	List<UserConcern> selectUserConcernByGameId(@Param("gameId1")int gameid1,@Param("gameId2")int gameid2);
	List<UserConcern> selectUserConcernByRange(@Param("infoRanges")List<Integer> infoRange);
}


