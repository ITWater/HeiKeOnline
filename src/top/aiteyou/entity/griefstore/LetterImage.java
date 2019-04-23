package top.aiteyou.entity.griefstore;

import java.io.Serializable;

/**
 * 信封
	*@author :tb
	*@version 2018年5月24日 下午4:21:47
*/

public class LetterImage implements Serializable{
	private static final long serialVersionUID = 1289324739724956426L;

	private Integer id;
	
	private String url;

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
	
	
}


