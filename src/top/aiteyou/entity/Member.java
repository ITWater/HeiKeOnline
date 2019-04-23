package top.aiteyou.entity;

import java.io.Serializable;

/**
 * 球队成员
 * @author tb
 * *@version 2018年3月16日 下午9:03:59
 */
public class Member implements Serializable{
	private static final long serialVersionUID = -5145346648552928336L;

	private Integer id;
    
    private Integer teamId;
    
    private String name;

    private String brithday;

    private String position;

    private String icon;

    private String signature;

    private String alias;
    
    private Integer number;
    

    //绑定类
    private Team team;

    
    
    
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", teamId=" + teamId + ", name=" + name + ", brithday=" + brithday + ", position="
				+ position + ", icon=" + icon + ", signature=" + signature + ", alias=" + alias + ", team=" + team
				+ "]";
	}
	


	
}