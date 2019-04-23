package top.aiteyou.entity;

import java.io.Serializable;

/**
 * 赛点
 * @author tb
 * *@version 2018年3月16日 下午9:07:41
 */
public class PointInfo implements Serializable{
	private static final long serialVersionUID = -1534198675040036997L;

	enum EventKind {
    	SCORE,YELLOCARD,READCARD,ChANGE
    }
    private Integer id;

    private Integer gameId;

    private Integer memberId;

    private String event;

    private String currentscore;// "3 : 1"
    
    private Integer happentime;//110'
    //绑定类
    private Member member;
    
    private EventKind eventKind;
    
    private String teamName;
    
    
    public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public EventKind getEventKind() {
		return eventKind;
	}

	public void setEventKind(EventKind eventKind) {
		this.eventKind = eventKind;
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

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCurrentscore() {
		return currentscore;
	}

	public void setCurrentscore(String currentscore) {
		this.currentscore = currentscore;
	}

	public Integer getHappentime() {
		return happentime;
	}

	public void setHappentime(Integer happentime) {
		this.happentime = happentime;
	}

	@Override
	public String toString() {
		return "PointInfo [id=" + id + ", gameId=" + gameId + ", memberId=" + memberId + ", event=" + event
				+ ", currentscore=" + currentscore + ", happentime=" + happentime + ", member=" + member
				+ ", eventKind=" + eventKind + "]";
	}


	
	
    
}