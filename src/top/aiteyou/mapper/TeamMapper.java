package top.aiteyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.Team;
@Repository
public interface TeamMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Team record);

    int insertSelective(Team record);


    Team selectByPrimaryKey(Integer id);
    
    Team selectJoinMembers(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

	List<Team> selctTeamsbrief(int seasonId);

	Team selectOneJoinGamesMembers(int id);
    
}