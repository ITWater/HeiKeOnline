package top.aiteyou.entity;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年5月9日 下午3:31:40
*/

public class AppStatus implements Serializable {
	private static final long serialVersionUID = 8767578805105315056L;

	private Integer id;
	
	private Integer appId;
	
	private boolean status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}


