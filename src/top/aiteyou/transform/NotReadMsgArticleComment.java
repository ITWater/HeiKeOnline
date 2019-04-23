package top.aiteyou.transform;

import java.util.List;

import top.aiteyou.entity.schoolArticle.ArticleComment;

/**
	*@author :tb
	*@version 2018年5月1日 上午11:00:02
*/

public class NotReadMsgArticleComment {
	private String kindof;
	
	private List<ArticleComment> value;

	public NotReadMsgArticleComment(String kindof, List<ArticleComment> comments) {
		super();
		this.kindof = kindof;
		this.value = comments;
	}

	public String getKindof() {
		return kindof;
	}

	public void setKindof(String kindof) {
		this.kindof = kindof;
	}

	public List<ArticleComment> getValue() {
		return value;
	}

	public void setValue(List<ArticleComment> value) {
		this.value = value;
	}

	
	
}


