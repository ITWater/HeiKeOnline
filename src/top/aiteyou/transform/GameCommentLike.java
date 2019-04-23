package top.aiteyou.transform;


import top.aiteyou.entity.GameComment;

/**
	*@author :tb
	*@version 2018年3月31日 上午10:20:50
*/

public class GameCommentLike extends GameComment {

	private static final long serialVersionUID = 6055638043063718322L;
	
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}


