package top.aiteyou.transform;

import java.io.Serializable;
import java.util.Date;

import top.aiteyou.controller.GameController;

/**
	*@author :tb
	*@version 2018年3月21日 下午3:43:19
*/

public class GameSelectFormat implements Serializable{
	private static final long serialVersionUID = 1489555865661363094L;
	private Integer seasonId;
	private Integer teamId;
	private String current;
	private Integer offset;
	private Integer limit;
	private String direction;
			/******   0-足球联赛  1-足球杯赛  2-男篮 3-女篮                 *****/
	private Integer gameId; 
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public GameSelectFormat() {
		//设好默认值
		if (current==null || current.equals("")) {
			current=GameController.dateFormat.format(new Date());
		}
		if (offset==null) {
			offset=0;
		}if (limit==null) {
			limit=50;
		}
		//设错误初始值
		if (seasonId==null) {
			seasonId=-1;
		}if (teamId==null) {
			teamId=-1;
		}
		if (gameId ==null) {
			gameId=0;//默认足球联赛
		}
	}
	
	
	
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}


