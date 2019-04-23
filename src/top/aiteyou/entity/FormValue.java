package top.aiteyou.entity;

import java.sql.Timestamp;

/**
	*@author :tb
	*@version 2018年4月2日 上午10:11:53
*/

public class FormValue {
	private int id;
	
	private int userId;
	
	private String formId;
	
	private Timestamp time;
	
	private String dbName;// 属于哪个表的
	
	private Integer appId;
	
	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}


