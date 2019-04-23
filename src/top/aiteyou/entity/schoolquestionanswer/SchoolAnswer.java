package top.aiteyou.entity.schoolquestionanswer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import top.aiteyou.entity.User;

/**
 *  校园问答-校园回答
	*@author :tb
	*@version 2018年4月16日 下午1:54:21
*/

public class SchoolAnswer implements Serializable{
	private static final long serialVersionUID = -5568935438248512241L;

	private Integer id;
	
	private Integer questionId;
	
	private Integer userId;
	
	private String content;
	
	private Integer likecount;
	
	private Integer disscount;
	
	private Timestamp time;
	
	private Integer status;
	
	//绑定类
	private User user;
	private SchoolQuestion question;
	private List<SchoolAnswerComment> comments;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
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
	public Integer getLikecount() {
		return likecount;
	}
	public void setLikecount(Integer likecount) {
		this.likecount = likecount;
	}
	public Integer getDisscount() {
		return disscount;
	}
	public void setDisscount(Integer disscount) {
		this.disscount = disscount;
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
	public SchoolQuestion getQuestion() {
		return question;
	}
	public void setQuestion(SchoolQuestion question) {
		this.question = question;
	}
	public List<SchoolAnswerComment> getComments() {
		return comments;
	}
	public void setComments(List<SchoolAnswerComment> comments) {
		this.comments = comments;
	}
	
	
	
}


