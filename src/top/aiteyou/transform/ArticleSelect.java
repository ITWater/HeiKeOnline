package top.aiteyou.transform;

/** 
 *  用于查询校园动态
	*@author :tb
	*@version 2018年4月11日 上午10:52:09
*/

public class ArticleSelect {
	private Integer offset;
	
	private Integer limit;
	
	private String[] concernIds;
	

	public ArticleSelect() {
		this.offset=0;
		this.limit=10;
		this.concernIds=null;
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

	public String[] getConcernIds() {
		return concernIds;
	}

	public void setConcernIds(String[] concernIds) {
		this.concernIds = concernIds;
	}


	
	
}


