package top.aiteyou.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import top.aiteyou.entity.Game;
import top.aiteyou.service.GameService;
import top.aiteyou.transform.GameSelectFormat;
import top.aiteyou.transform.GameSupport;
import top.aiteyou.transform.GameTimeFormat;

/**
	*@author :tb
	*@version 2018年3月20日 下午2:14:11
*/

@Controller
@RequestMapping(value="/games")
public class GameController {
	public static DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat mouthFormat=new SimpleDateFormat("MM-dd");
	public static DateFormat hourFormat=new SimpleDateFormat("HH:mm");
	public static DateFormat mouthhourFormat=new SimpleDateFormat("MM-dd HH:mm");
	@Autowired
	private GameService gameService=null;
	
	
	/**
	 * 更新比赛
	 * @param game
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Game updateGameInfo(@RequestBody Game game){
		gameService.updateGame(game);
		gameService.deleteBriefCache();
		return game;
	}
	/**
	 * 给队伍加油
	 * @param gameSupport
	 * @return
	 */
	@RequestMapping(value="/addSupport/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int addTeamSupportCount(@RequestBody GameSupport gameSupport){
		return gameService.addTeamSupportCount(gameSupport);
	}
	
	/**
	 * 查询赛事详情
	 * @param id	
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@ResponseBody
	public Game selectOne(@PathVariable("id")int id){
		
		Game game=gameService.getOneDetailInfo(id);
		String time_str=hourFormat.format(game.getTime());
		game.setTime_str(time_str);
		return game;
	}
	
	private static String ORDERBY="time";
	/**
	 * 用于 首页 今日和未来的比赛简介
	 * @param seasonId 可选
	 * @param teamId 可选
	 * @param currnt 可选 ex:2018-3-21
	 * @param offset 可选
	 * @param limit 可选
	 * @return json数据
	 * @throws ParseException
	 */
	@RequestMapping(value="/list/future",method=RequestMethod.POST)
	@ResponseBody							
	public Collection<GameTimeFormat> selectRecentlyGames(@RequestBody(required=false) GameSelectFormat gameSelectFormat) throws ParseException {
		if (gameSelectFormat==null) {
			gameSelectFormat=new GameSelectFormat();
		}
		RowBounds rowBounds=new RowBounds(gameSelectFormat.getOffset(), gameSelectFormat.getLimit());
		List<Game> list=
			gameService.selectTodayFutureGames(gameSelectFormat.getGameId(),gameSelectFormat.getSeasonId(), gameSelectFormat.getTeamId()
					, rowBounds, gameSelectFormat.getCurrent(),gameSelectFormat.getDirection(), ORDERBY);
		Map<String, GameTimeFormat> map=new LinkedHashMap<>();
		for (Game game : list) {
			String time=mouthFormat.format(game.getTime());
			String hour=hourFormat.format(game.getTime());
			game.setTime_str(hour);
			if (!map.containsKey(time)) {
				List<Game> games=new ArrayList<>();
				GameTimeFormat gameTimeFormat=new GameTimeFormat(time,games);
				map.put(time,gameTimeFormat );
			}
			map.get(time).getGames().add(game);
		}
		return map.values();
	}
	/**
	 * 用于 首页 今日和以前的比赛简介
	 * @param seasonId 可选
	 * @param teamId 可选
	 * @param currnt 可选 ex:2018-3-21
	 * @param offset 可选
	 * @param limit 可选
	 * @return json数据
	 * @throws ParseException
	 */
	@RequestMapping(value="/list/ago",method=RequestMethod.POST)
	@ResponseBody							
	public Collection<GameTimeFormat> selectRecentlyAGOGames(@RequestBody(required=false) GameSelectFormat gameSelectFormat) throws ParseException {
		if (gameSelectFormat==null) {
			gameSelectFormat=new GameSelectFormat();
		}
		RowBounds rowBounds=new RowBounds(gameSelectFormat.getOffset(), gameSelectFormat.getLimit());
		List<Game> list=
				gameService.selectTodayOGoGames(gameSelectFormat.getGameId(),gameSelectFormat.getSeasonId(), gameSelectFormat.getTeamId()
						, rowBounds, gameSelectFormat.getCurrent(),gameSelectFormat.getDirection(), ORDERBY);
		Map<String, GameTimeFormat> map=new LinkedHashMap<>();
		for (Game game : list) {
			String time=mouthFormat.format(game.getTime());
			String hour=hourFormat.format(game.getTime());
			game.setTime_str(hour);
			if (!map.containsKey(time)) {
				List<Game> games=new ArrayList<>();
				GameTimeFormat gameTimeFormat=new GameTimeFormat(time,games);
				map.put(time,gameTimeFormat );
			}
			map.get(time).getGames().add(game);
		}
		return map.values();
	}
	
	@ExceptionHandler(ParseException.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR,reason="时间解析错误")
	public String HandleParseException(ParseException exception){
		Logger.getLogger(getClass()).error("时间解析错误"+exception.toString());
		return "error";
	}
}


