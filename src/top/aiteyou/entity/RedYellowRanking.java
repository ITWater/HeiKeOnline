package top.aiteyou.entity;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年4月8日 上午11:31:07
*/

public class RedYellowRanking implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer teamId;
	
	private Integer memberId;
	
	private Integer redcount;
	
	private Integer yellowcount;
	
	private Integer ranking;
	
	//绑定类
	private Team team;
	private Member member;
	
	
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
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
	public Integer getRedcount() {
		return redcount;
	}
	public void setRedcount(Integer redcount) {
		this.redcount = redcount;
	}
	public Integer getYellowcount() {
		return yellowcount;
	}
	public void setYellowcount(Integer yellowcount) {
		this.yellowcount = yellowcount;
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
	
	
	
	
	
}


