package top.aiteyou.mapper.schoolarticle;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolArticle.UserConcernInfo;


/**
	*@author :tb
	*@version 2018年4月21日 上午9:39:11
*/
@Repository
public interface UserConcernInfoMapper {
	int insertConcern(UserConcernInfo userConcernInfo);
	
	int deleteConcern(@Param("userId")int userId,@Param("concernUserId") int concernUserId);
	
}


