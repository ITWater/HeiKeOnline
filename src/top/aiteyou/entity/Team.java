package top.aiteyou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 球队
 * @author tb
 * *@version 2018年3月16日 下午9:12:24
 */
public class Team implements Serializable{
	
	private static final long serialVersionUID = -1374371659088606934L;

	private Integer id;
    
    private String name;
    
    private String log;

    private String belongs;

    private String info;

    private String picturesString;

    private String honorString;
    
    private int captainId;
    
    private Integer kindOf;
    
    //绑定类
    private Member captain;
    
    public Integer getKindOf() {
		return kindOf;
	}
	public void setKindOf(Integer kindOf) {
		this.kindOf = kindOf;
	}
	private List<Member> members;
    private List<Game> games;
    private List<Season> seasons;
    
    
	public Team() {
	}
	public Team(String name2, String log2) {
		this.name=name2;
		this.log=log2;
	}
	public int getCaptainId() {
		return captainId;
	}
	public void setCaptainId(int captainId) {
		this.captainId = captainId;
	}
	public Member getCaptain() {
		return captain;
	}
	public void setCaptain(Member captain) {
		this.captain = captain;
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
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getBelongs() {
		return belongs;
	}
	public void setBelongs(String belongs) {
		this.belongs = belongs;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getPicturesString() {
		return picturesString;
	}
	public void setPicturesString(String picturesString) {
		this.picturesString = picturesString;
	}
	public String getHonorString() {
		return honorString;
	}
	public void setHonorString(String honorString) {
		this.honorString = honorString;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public List<Game> getGames() {
		return games;
	}
	public void setGames(List<Game> games) {
		this.games = games;
	}
	public List<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}
	
	
	
  
}