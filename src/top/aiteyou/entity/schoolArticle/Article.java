package top.aiteyou.entity.schoolArticle;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


import top.aiteyou.entity.User;

/**
 *  校圈动态
	*@author :tb
	*@version 2018年4月11日 上午10:37:44
*/

public class Article implements Serializable{

	private static final long serialVersionUID = -6415562738938607227L;

	private Integer id;
	
	private Integer userId;
	
	private String text;
	
	private String videoCoverUrl;
	
	private String videoId;
	
	private String playUrl;
	
	private Integer likecount;
	
	private Integer commentcount;
	
	private Integer viewcount;
	
	private Timestamp time;
	
	private String fromdevice;
	
	private Integer status;
	
	private String[] imgurls_str;
	
	//绑定类
	private User user;
	private List<ArticleComment> articleComments;
	private List<ArticlePicture> articlePictures;
	
	
	

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public Article() {
		imgurls_str=new String[0];
	}

	public String[] getImgurls_str() {
		return imgurls_str;
	}

	public void setImgurls_str(String[] imgurls_str) {
		this.imgurls_str = imgurls_str;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVideoCoverUrl() {
		return videoCoverUrl;
	}

	public void setVideoCoverUrl(String videoCoverUrl) {
		this.videoCoverUrl = videoCoverUrl;
	}


	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public List<ArticlePicture> getArticlePictures() {
		return articlePictures;
	}

	public void setArticlePictures(List<ArticlePicture> articlePictures) {
		this.articlePictures = articlePictures;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ArticleComment> getArticleComments() {
		return articleComments;
	}

	public void setArticleComments(List<ArticleComment> articleComments) {
		this.articleComments = articleComments;
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


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLikecount() {
		return likecount;
	}

	public void setLikecount(Integer likecount) {
		this.likecount = likecount;
	}

	public Integer getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Integer commentcount) {
		this.commentcount = commentcount;
	}

	public Integer getViewcount() {
		return viewcount;
	}

	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getFromdevice() {
		return fromdevice;
	}

	public void setFromdevice(String fromdevice) {
		this.fromdevice = fromdevice;
	}
	
	
}


