package top.aiteyou.entity;

import java.sql.Timestamp;

import sun.nio.cs.ext.TIS_620;

/**
 *  邀请码
	*@author :tb
	*@version 2018年4月26日 下午1:05:13
*/

public class InviteCode {
	private Integer id;
	
	private String code;
	
	private Integer kindof;
	
	private boolean isUse;
	
	private Integer userId;
	
	private Timestamp time;

	
	
	
	public InviteCode() {
	}

	public InviteCode(String code,Timestamp time,Integer kindof) {
		this.code=code;
		this.time=time;
		this.kindof=kindof;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Integer getKindof() {
		return kindof;
	}

	public void setKindof(Integer kindof) {
		this.kindof = kindof;
	}

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
	
}


