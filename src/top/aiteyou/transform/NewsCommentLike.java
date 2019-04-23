package top.aiteyou.transform;


import top.aiteyou.entity.NewsComment;

/**
	*@author :tb
	*@version 2018年3月31日 上午10:26:47
*/

public class NewsCommentLike extends NewsComment {
	private static final long serialVersionUID = -3640729997258380872L;
	
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}


