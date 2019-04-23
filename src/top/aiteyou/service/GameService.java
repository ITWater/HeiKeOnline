package top.aiteyou.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import top.aiteyou.entity.Game;
import top.aiteyou.transform.GameSupport;

/**
 *  赛事业务
	*@author :tb
	*@version 2018年3月19日 上午11:52:43
*/

public interface GameService {
	
	public List<Game> list();
	
	public List<Game> list(Timestamp start,Timestamp end);
	
	public Game getOneDetailInfo(int id);
	
	public List<Game> list(int seasonid);
	
	public Game insertGame(Game game);

	public List<Game> selectTodayFutureGames(int gameId,int seasonId, int teamId, RowBounds rowBounds,String currnt,String direction,String orderBy) throws ParseException ;

	public int addTeamSupportCount(GameSupport gameSupport);

	List<Game> selectTodayOGoGames(int gameId,int seasonId, int teamId, RowBounds rowBounds, String current, String direction,
			String orderBy) throws ParseException;

	public void updateGame(Game game);

	public int deleteBriefCache();
	
	
}


