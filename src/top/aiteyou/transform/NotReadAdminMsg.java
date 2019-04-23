package top.aiteyou.transform;

import java.util.List;

import top.aiteyou.entity.AdminMsg;

/**
	*@author :tb
	*@version 2018年5月4日 下午8:20:59
*/

public class NotReadAdminMsg {
	private String kindof;
	
	private List<AdminMsg> value;

	
	
	public NotReadAdminMsg(String kindof, List<AdminMsg> value) {
		super();
		this.kindof = kindof;
		this.value = value;
	}

	public NotReadAdminMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getKindof() {
		return kindof;
	}

	public void setKindof(String kindof) {
		this.kindof = kindof;
	}

	public List<AdminMsg> getValue() {
		return value;
	}

	public void setValue(List<AdminMsg> value) {
		this.value = value;
	}
	
	
}


