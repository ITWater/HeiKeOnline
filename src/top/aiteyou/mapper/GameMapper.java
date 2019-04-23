package top.aiteyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.Game;
import top.aiteyou.transform.GameSupport;
@Repository
public interface GameMapper {


    int deleteByPrimaryKey(Integer id);


    int insertSelective(Game record);

    List<Game> selectTodayFutureGames(@Param("gameId")int gameId,@Param("seasonId") int seasonId,@Param("teamId") int teamId,
    		RowBounds rowBounds,@Param("current") String current,@Param("direction")String direction,@Param("orderBy")String orderBy);
    
    List<Game> selectTodayOGoGames(@Param("gameId")int gameId,@Param("seasonId") int seasonId,@Param("teamId") int teamId,
    		RowBounds rowBounds,@Param("current") String current,@Param("direction")String direction,@Param("orderBy")String orderBy);
    
    List<Game> selectRecentlyGameBriefsByTeamId(@Param("teamId") int teamId);
    
    Game selectOneDetails(@Param("gameId") int gameId);
    

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);


	int addTeamSupportCount(GameSupport gameSupport);


	List<Game> selectTorrowGameJoinTeam(@Param("tomorrow")String tomorrow_str,@Param("thedayaftertomorrow") String thedayaftertomorrow,@Param("gameId")int gameId);
	
	List<Game> selectAllGameByGameIdOrderByTimeDesc(@Param("gameId") int gameId);


	Game selectOneJoinTeam(@Param("gameId")Integer gameId);


	Game selectOneBaseInfo(@Param("gameId")Integer gameId);


}