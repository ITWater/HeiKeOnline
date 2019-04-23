package top.aiteyou.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.Game;
import top.aiteyou.mapper.GameMapper;
import top.aiteyou.transform.GameSupport;

/**
	*@author :tb
	*@version 2018年3月19日 下午7:01:30
*/
@Service
public class GameServiceImpl implements GameService {
	
	@Autowired 
	private GameMapper gameMapper=null;
	@Override
	public List<Game> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> list(Timestamp start, Timestamp end) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Cacheable(value="games_detail",key="'game_id' + #id")
	@Override
	public Game getOneDetailInfo(int id) {
		return gameMapper.selectOneDetails(id);
	}

	@Override
	public List<Game> list(int seasonid) {
		// TODO Auto-generated method stub
		return null;
	}
	/*@CachePut(value="games",key="''+#game.id")
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	@Override
	public Game insertGame(Game game) {
		gameMapper.insertSelective(game);//带主键生成
		return game;
	}*/
	
	@Override
	public Game insertGame(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Calendar calendar=Calendar.getInstance();//注意使用 不得用此对象获取当前时间只能用于计算
	/**
	 * 查询最近的比赛
	 * @return
	 * @throws ParseException 
	 */
	@Cacheable(value="gamebriefs",key="'game'+#gameId + '_se'+#seasonId + '_te'+#teamId +"
			+ " '_ct'+#current + '_directionfuture' +'_ob'+#orderBy +'_offset'+#rowBounds.offset"
			+ " +'_limit'+#rowBounds.limit")
	@Override
	public List<Game> selectTodayFutureGames(int gameId,int seasonId, int teamId, RowBounds rowBounds,String current,String direction,String orderBy) throws ParseException {
		
		
		return gameMapper.selectTodayFutureGames(gameId,seasonId, teamId,rowBounds,current,direction, orderBy);
	}
	
	@Cacheable(value="gamebriefs",key="'game'+#gameId + '_se'+#seasonId + '_te'+#teamId +"
			+ " '_ct'+#current + 'directionago'  +'_ob'+#orderBy +'_offset'+#rowBounds.offset"
			+ " +'_limit'+#rowBounds.limit")
	@Override
	public List<Game> selectTodayOGoGames(int gameId,int seasonId, int teamId, RowBounds rowBounds,String current,String direction,String orderBy) throws ParseException {
		
		return gameMapper.selectTodayOGoGames(gameId,seasonId, teamId,rowBounds,current,direction, orderBy);
	}
	/**
	 * 删除此比赛详情的缓存
	 */
	@CacheEvict(value="games_detail",key="'game_id' + #gameSupport.gameId")
	@Override
	public int addTeamSupportCount(GameSupport gameSupport) {
		return gameMapper.addTeamSupportCount(gameSupport);
	}
	
	
	@Override
	@CacheEvict(value="games_detail",key="'game_id' + #game.id")
	public void updateGame(Game game) {
		gameMapper.updateByPrimaryKeySelective(game);
	}

	@Override
	@CacheEvict(value="gamebriefs",allEntries=true)
	public int deleteBriefCache() {
		int i=1+1;
		return i;
	}

	

}


