package top.aiteyou.entity.schoolArticle;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import top.aiteyou.entity.User;

/**
 *  动态评论
	*@author :tb
	*@version 2018年4月11日 上午11:44:36
*/

public class ArticleComment implements Serializable{
	private static final long serialVersionUID = 7336929628079596950L;

	private Integer id;
	
	private Integer articleId;
	
	private Integer userId;
	
	private String text;
	
	private String picture;
	
	private Integer likecount;
	
	private Integer commentcount;//二次评论的数量
	
	private Timestamp time;
	
	private Integer status;
	
	//绑定类
	private User user;
	private List<ArticleCommentToComment> commentToComments;//评论的评论s

	
	

	public Integer getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Integer commentcount) {
		this.commentcount = commentcount;
	}

	public List<ArticleCommentToComment> getCommentToComments() {
		return commentToComments;
	}

	public void setCommentToComments(List<ArticleCommentToComment> commentToComments) {
		this.commentToComments = commentToComments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
	
	
	

}


