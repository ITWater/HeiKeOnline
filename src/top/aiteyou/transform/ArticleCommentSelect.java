package top.aiteyou.transform;

/**  
 * 获取更多动态评论
	*@author :tb
	*@version 2018年4月11日 下午12:26:59
*/

public class ArticleCommentSelect {
	private Integer articleId;
	
	private Integer offset;
	
	private Integer limit;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
}


