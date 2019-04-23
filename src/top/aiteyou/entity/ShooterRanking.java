package top.aiteyou.entity;

import java.io.Serializable;

/**	
 * 
	*@author :tb
	*@version 2018年4月8日 上午11:00:24
*/

public class ShooterRanking  implements Serializable{
	
	private static final long serialVersionUID = -3539795702724201102L;

	private Integer id;
	
	private Integer teamId;
	
	private Integer memberId;
	
	private Integer goal;
	
	private Integer ranking;
	
	private Integer gameId;
	
	//绑定类
	private Team team;
	
	private Member member;
	
	

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	

}


