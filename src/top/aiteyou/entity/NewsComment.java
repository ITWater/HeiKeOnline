package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 	用户资讯评论
	*@author :tb
	*@version 2018年3月22日 上午11:09:27
*/

public class NewsComment implements Serializable{
	private static final long serialVersionUID = -9080744157747633181L;

	private Integer id;
	
	private Integer userId;
	
	private Integer newsId;
	
	private Timestamp time;
	
	private String content;
	
	private Integer likecount;
	
	private Integer status;
	
	//绑定类
	private User user;
	private News news;
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
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
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
	
}


