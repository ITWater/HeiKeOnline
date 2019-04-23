package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import sun.nio.cs.ext.TIS_620;

/**
	*@author :tb
	*@version 2018年5月1日 下午1:36:20
*/

public class SystemMsg implements Serializable {
	private static final long serialVersionUID = 1658900707617808974L;

	private Integer userId;
	
	private String text;
	
	private Timestamp time;
	
	private Integer importantLevel;
	
	public SystemMsg(Integer userId, String text,Timestamp time) {
		super();
		this.userId = userId;
		this.text = text;
		this.time=time;
	}


	public Integer getImportantLevel() {
		return importantLevel;
	}


	public void setImportantLevel(Integer importantLevel) {
		this.importantLevel = importantLevel;
	}


	public SystemMsg() {
		this.importantLevel=10;
	}


	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}


