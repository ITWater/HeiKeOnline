package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *  资讯
	*@author :tb
	*@version 2018年3月22日 上午11:12:04
*/

public class News implements Serializable{
	private static final long serialVersionUID = -2845091984701924129L;

	private int id;
	
	private int userId;
	
	private String content;
	
	private String title;
	
	private Timestamp time;
	
	private String headImage;
	
	private int likecount;
	
	private int commentCount;
	
	private int viewCount;
	
	private String kindof;
	
	private int weight;
	
	private String[] content_str;
	
	private String briefInfo;
	
	private Integer isbanner;
	
	private Integer status;
	
	private String authorTemp;
	
	private String webViewPageUrl;
	
	//绑定类
	private User author;
	private List<NewsComment> newsComments;//资讯用户评论
	
	
	
	public String getWebViewPageUrl() {
		return webViewPageUrl;
	}
	public void setWebViewPageUrl(String webViewPageUrl) {
		this.webViewPageUrl = webViewPageUrl;
	}
	public String getAuthorTemp() {
		return authorTemp;
	}
	public void setAuthorTemp(String authorTemp) {
		this.authorTemp = authorTemp;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsbanner() {
		return isbanner;
	}
	public void setIsbanner(Integer isbanner) {
		this.isbanner = isbanner;
	}
	public String getBriefInfo() {
		return briefInfo;
	}
	public void setBriefInfo(String briefInfo) {
		this.briefInfo = briefInfo;
	}
	public String[] getContent_str() {
		return content_str;
	}
	public void setContent_str(String[] content_str) {
		this.content_str = content_str;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	public int getLikecount() {
		return likecount;
	}
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getKindof() {
		return kindof;
	}
	public void setKindof(String kindof) {
		this.kindof = kindof;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<NewsComment> getNewsComments() {
		return newsComments;
	}
	public void setNewsComments(List<NewsComment> newsComments) {
		this.newsComments = newsComments;
	}
	
	
	
	
	
}


