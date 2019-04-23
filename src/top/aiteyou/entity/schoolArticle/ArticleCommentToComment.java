package top.aiteyou.entity.schoolArticle;

import java.io.Serializable;
import java.sql.Timestamp;

import top.aiteyou.entity.User;

/**
 *  校园评论的评论
	*@author :tb
	*@version 2018年4月21日 下午9:30:01
*/

public class ArticleCommentToComment implements Serializable{

	private static final long serialVersionUID = -5779698519036636972L;

	private Integer id;
	
	private Integer commentId;
	
	private Integer userId;
	
	private String content;
	
	private Integer likecount;
	
	private Timestamp time;
	
	private Integer status;
	
	
	//绑定类
	private User user;

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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
	
	
}


