package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:11:28
*/

public class HitMorningImage implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String url;
	
	private Timestamp time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}


