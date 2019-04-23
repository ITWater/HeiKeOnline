package top.aiteyou.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.GameProgressStatus;


/**
	*@author :tb
	*@version 2018年4月15日 下午12:42:33
*/
@Repository
public interface GameProgressStatusMapper {
 
	GameProgressStatus selectByPrimaryKey(@Param("gameId")Integer gameId);
	
}


