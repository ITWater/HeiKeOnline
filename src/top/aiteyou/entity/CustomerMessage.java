package top.aiteyou.entity;

import java.sql.Timestamp;

/**
	*@author :tb
	*@version 2018年5月31日 下午8:38:02
*/

public class CustomerMessage {
	private Integer id;
	
	private String fromPeopleId;
	
	private String content;
	
	private Timestamp time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromPeopleId() {
		return fromPeopleId;
	}

	public void setFromPeopleId(String fromPeopleId) {
		this.fromPeopleId = fromPeopleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public CustomerMessage(Integer id, String fromPeopleId, String content, Timestamp time) {
		super();
		this.id = id;
		this.fromPeopleId = fromPeopleId;
		this.content = content;
		this.time = time;
	}

	public CustomerMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}


