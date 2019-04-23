package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
	*@author :tb
	*@version 2018年4月8日 下午12:04:46
*/

public class HitMorningText implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String content;
	
	private Timestamp time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	
}


