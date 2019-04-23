package top.aiteyou.transform.schoolquestionanswer;

/** 
 * 提供查询校园问题的信息
	*@author :tb
	*@version 2018年4月16日 下午5:49:45
*/

public class SchoolQuestionSelect {
	private Integer offset;
	
	private Integer limit;
	
	private SchoolQuestionQueryType queryType;
	
	public static enum SchoolQuestionQueryType{
		ByQuestionType,//问题的类型
		ForNewest,//最新的
		TopHot,//最热的、最多人参与
		NoPerfectAnswer//还没有最佳回答的
	}
	//可选项
	private String choiceType;//ex: #类型1;#类型2

	
	
	
	public SchoolQuestionSelect() {
		this.offset=0;
		this.limit=10;
		this.queryType=SchoolQuestionQueryType.ForNewest;
	}
	
	public String getChoiceType() {
		return choiceType;
	}

	public void setChoiceType(String choiceType) {
		this.choiceType = choiceType;
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

	public SchoolQuestionQueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(SchoolQuestionQueryType queryType) {
		this.queryType = queryType;
	}
	
	
}


