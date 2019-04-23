package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import top.aiteyou.entity.PointInfo.EventKind;

/**
 * 比赛
 * @author tb
 * *@version 2018年3月16日 下午8:58:20
 */
public class Game implements Serializable{
	enum GameStatus{
		未开赛,
		已结束,
		进行中,
	}
	private static final long serialVersionUID = -1757936577319671208L;

	private Integer id;

    private String name;

    private Integer seasonId;

    private Integer team1Id;

    private Short score1;

    private Integer team2Id;

    private Short score2;
    
    private String location;
    
    private Timestamp time;
    
    private String time_str;
    
    private GameStatus status;//ex: 未开赛
    
    private int team1likecount;
    
    private int team2likecount;
    
    private String mainjudge;
    
    private String otherjudger1;
    
    private String otherjudger2;

    private Integer gameId;
    //绑定类
    
    private Team team1; 
    private Team team2; 
    private Season season;
    private List<PointInfo> pointInfos;
    private List<GameComment> comments;
    
    
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public String getTime_str() {
		return time_str;
	}
	public void setTime_str(String time_str) {
		this.time_str = time_str;
	}
	public int getTeam1likecount() {
		return team1likecount;
	}
	public void setTeam1likecount(int team1likecount) {
		this.team1likecount = team1likecount;
	}
	public int getTeam2likecount() {
		return team2likecount;
	}
	public void setTeam2likecount(int team2likecount) {
		this.team2likecount = team2likecount;
	}
	public String getMainjudge() {
		return mainjudge;
	}
	public void setMainjudge(String mainjudge) {
		this.mainjudge = mainjudge;
	}
	public String getOtherjudger1() {
		return otherjudger1;
	}
	public void setOtherjudger1(String otherjudger1) {
		this.otherjudger1 = otherjudger1;
	}
	public String getOtherjudger2() {
		return otherjudger2;
	}
	public void setOtherjudger2(String otherjudger2) {
		this.otherjudger2 = otherjudger2;
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
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public Short getScore1() {
		return score1;
	}
	public void setScore1(Short score1) {
		this.score1 = score1;
	}
	
	public Integer getTeam1Id() {
		return team1Id;
	}
	public void setTeam1Id(Integer team1Id) {
		this.team1Id = team1Id;
	}
	public Integer getTeam2Id() {
		return team2Id;
	}
	public void setTeam2Id(Integer team2Id) {
		this.team2Id = team2Id;
	}
	public Short getScore2() {
		return score2;
	}
	public void setScore2(Short score2) {
		this.score2 = score2;
	}
	public Team getTeam1() {
		return team1;
	}
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}
	public Team getTeam2() {
		return team2;
	}
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
	public List<PointInfo> getPointInfos() {
		return pointInfos;
	}
	public void setPointInfos(List<PointInfo> pointInfos) {
		this.pointInfos = pointInfos;
	}
	public List<GameComment> getComments() {
		return comments;
	}
	public void setComments(List<GameComment> comments) {
		this.comments = comments;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
    
   
}