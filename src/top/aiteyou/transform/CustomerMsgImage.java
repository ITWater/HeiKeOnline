package top.aiteyou.transform;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年5月31日 下午9:15:41
*/

public class CustomerMsgImage implements Serializable{
	private static final long serialVersionUID = -30700532085818264L;

	private String title;
	
	private String description;
	
	private String url;
	
	private String thumb_url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public CustomerMsgImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerMsgImage(String title, String description, String url, String thumb_url) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.thumb_url = thumb_url;
	}
	
}

