package top.aiteyou.transform;

import java.util.List;

import top.aiteyou.entity.SystemMsg;

/**
	*@author :tb
	*@version 2018年5月1日 下午1:40:50
*/

public class NotReadSystemMsg {
	private String kindof;
	
	private List<SystemMsg> value;

	public NotReadSystemMsg(String kindof, List<SystemMsg> value) {
		super();
		this.kindof = kindof;
		this.value = value;
	}

	public String getKindof() {
		return kindof;
	}

	public void setKindof(String kindof) {
		this.kindof = kindof;
	}

	public List<SystemMsg> getValue() {
		return value;
	}

	public void setValue(List<SystemMsg> value) {
		this.value = value;
	}
	
}


