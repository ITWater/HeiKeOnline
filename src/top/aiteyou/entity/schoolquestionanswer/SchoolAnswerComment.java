package top.aiteyou.entity.schoolquestionanswer;

import java.io.Serializable;
import java.sql.Timestamp;

import top.aiteyou.entity.User;

/**  
 *  校园问答-校园问答评论
	*@author :tb
	*@version 2018年4月16日 下午1:51:26
*/

public class SchoolAnswerComment implements Serializable{
	private static final long serialVersionUID = 8660732525281765629L;

	private Integer id;
	
	private Integer answerId;
	
	private Integer userId;
	
	private String content;
	
	private Integer likecount;
	
	private Timestamp time;
	
	private Integer status;
	
	
	
	
	//绑定类
	private User user;
	private SchoolAnswer answer;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
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
	public SchoolAnswer getAnswer() {
		return answer;
	}
	public void setAnswer(SchoolAnswer answer) {
		this.answer = answer;
	}
	
	
	
	
	
	
	
	
}


