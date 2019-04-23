package top.aiteyou.transform;

/**
 *  资讯浏览
	*@author :tb
	*@version 2018年4月19日 下午9:24:49
*/

public class NewsView {
	private Integer newsId;
	
	private Integer count;

	public NewsView(int newsId, int count) {
		this.newsId=newsId;
		this.count=count;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}


