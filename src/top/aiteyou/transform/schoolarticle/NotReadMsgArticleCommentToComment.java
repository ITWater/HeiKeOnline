package top.aiteyou.transform.schoolarticle;

import java.util.List;

import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;

/**
	*@author :tb
	*@version 2018年5月6日 上午10:48:36
*/

public class NotReadMsgArticleCommentToComment {
	private String kindof;
	
	private List<ArticleCommentToComment> value;

	
	
	
	public NotReadMsgArticleCommentToComment(String kindof, List<ArticleCommentToComment> value) {
		super();
		this.kindof = kindof;
		this.value = value;
	}

	public NotReadMsgArticleCommentToComment() {
	}

	public String getKindof() {
		return kindof;
	}

	public void setKindof(String kindof) {
		this.kindof = kindof;
	}

	public List<ArticleCommentToComment> getValue() {
		return value;
	}

	public void setValue(List<ArticleCommentToComment> value) {
		this.value = value;
	}
	
	
	

}


