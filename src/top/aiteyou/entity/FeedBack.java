package top.aiteyou.entity;

import java.sql.Timestamp;

/**
	*@author :tb
	*@version 2018年4月2日 下午8:44:41
*/

public class FeedBack {
	private Integer id;
	
	private Integer userId;
	
	private String current;//笔误  (:
	
	private Integer status; 
	
	private Timestamp time;
	
	private String replyContent;
	

	
	
	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
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

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}
	
	
}


