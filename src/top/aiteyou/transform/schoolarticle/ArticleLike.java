package top.aiteyou.transform.schoolarticle;

/**
 *  校园动态点赞
	*@author :tb
	*@version 2018年4月22日 下午5:12:07
*/

public class ArticleLike {
	private Integer articleId;
	
	private Integer count;// cancle =-1 

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}


