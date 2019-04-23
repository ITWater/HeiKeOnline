package top.aiteyou.entity;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年4月8日 上午9:33:32
*/

public class ScoreRanking implements Serializable{
	private static final long serialVersionUID = -5399499005371034183L;

	private Integer id;
	
	private Integer teamId;
	
	private Integer ord;//比赛轮次
	
	private Integer win;
	
	private Integer draw;
	
	private Integer defeated;
	
	private Integer goal;
	
	private Integer lost;
	
	private Integer realgoal;
	
	private Integer score;

	private Integer ranking;
	
	private Integer gameId;
	
	private String groupName;
	
	
	//绑定类
	private Team team;


	
	
	public Integer getGameId() {
		return gameId;
	}


	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public Integer getRanking() {
		return ranking;
	}


	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}


	public Integer getGoal() {
		return goal;
	}


	public void setGoal(Integer goal) {
		this.goal = goal;
	}


	public Integer getLost() {
		return lost;
	}


	public void setLost(Integer lost) {
		this.lost = lost;
	}


	public Integer getRealgoal() {
		return realgoal;
	}


	public void setRealgoal(Integer realgoal) {
		this.realgoal = realgoal;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getTeamId() {
		return teamId;
	}


	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}


	public Integer getOrd() {
		return ord;
	}


	public void setOrd(Integer ord) {
		this.ord = ord;
	}


	public Integer getWin() {
		return win;
	}


	public void setWin(Integer win) {
		this.win = win;
	}


	public Integer getDraw() {
		return draw;
	}


	public void setDraw(Integer draw) {
		this.draw = draw;
	}


	public Integer getDefeated() {
		return defeated;
	}


	public void setDefeated(Integer defeated) {
		this.defeated = defeated;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	
	
}


