package top.aiteyou.entity.schoolquestionanswer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import top.aiteyou.entity.User;

/**
	*@author :tb
	*@version 2018年4月16日 下午1:57:33
*/

public class SchoolQuestion implements Serializable {
	private static final long serialVersionUID = 7487379539372936585L;

	private Integer id;
	
	private String type;
	
	private Integer userId;
	
	private String title;
	
	private String content;
	
	private Integer viewCount;
	
	private Integer answerCount;
	
	private Integer perfectanswerId;

	private Timestamp time;
	
	private Integer status;
	
	
	
	//绑定类
	private User user;
	private List<SchoolAnswer> answers;
	
	
	
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public Integer getPerfectanswerId() {
		return perfectanswerId;
	}
	public void setPerfectanswerId(Integer perfectanswerId) {
		this.perfectanswerId = perfectanswerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<SchoolAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<SchoolAnswer> answers) {
		this.answers = answers;
	}
	
	
	
	
	
}


