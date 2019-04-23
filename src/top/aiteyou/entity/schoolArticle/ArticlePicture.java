package top.aiteyou.entity.schoolArticle;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年4月11日 下午1:34:34
*/

public class ArticlePicture implements Serializable{
	private static final long serialVersionUID = -7877453858342992909L;

	private Integer id;
	
	private Integer articleId;
	
	private String picture;

	
	
	public ArticlePicture() {
	}

	public ArticlePicture(Integer articleId, String imgurl) {
		this.articleId=articleId;
		this.picture=imgurl;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
}


