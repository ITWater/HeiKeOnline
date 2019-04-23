package top.aiteyou.entity.schoolquestionanswer;

import java.io.Serializable;

/** 校园问答标签
	*@author :tb
	*@version 2018年4月16日 下午9:05:06
*/

public class SchoolQuestionLabel implements Serializable {
	private static final long serialVersionUID = 7680606027433727911L;

	private Integer id;
	
	private String text;
	
	private String icon;
	
	private Integer useNumber;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(Integer useNumber) {
		this.useNumber = useNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}


