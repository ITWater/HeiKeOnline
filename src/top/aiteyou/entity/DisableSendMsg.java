package top.aiteyou.entity;

import java.sql.Timestamp;

import top.aiteyou.service.UserService;

/**
 * 禁言
	*@author :tb
	*@version 2018年5月6日 下午2:01:12
*/

public class DisableSendMsg {
	private Integer id;
	
	private Integer userId;
	
	private Integer status;
	
	private Timestamp time;
	
	private Integer minutes;
	
	//绑定类
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	 
}


