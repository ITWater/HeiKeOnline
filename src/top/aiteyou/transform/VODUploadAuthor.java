package top.aiteyou.transform;

import java.io.Serializable;

/**
 *  上传视频的凭证和地址
	*@author :tb
	*@version 2018年4月12日 下午12:53:12
*/

public class VODUploadAuthor implements Serializable{
	private static final long serialVersionUID = 6848852182749689755L;

	private String uploadAuth;
	
	private String uploadAddress;
	
	private String videoId;
	
	private String requestId;

	public String getUploadAuth() {
		return uploadAuth;
	}

	public void setUploadAuth(String uploadAuth) {
		this.uploadAuth = uploadAuth;
	}

	public String getUploadAddress() {
		return uploadAddress;
	}

	public void setUploadAddress(String uploadAddress) {
		this.uploadAddress = uploadAddress;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public VODUploadAuthor(String uploadAuth, String uploadAddress, String videoId, String requestId) {
		super();
		this.uploadAuth = uploadAuth;
		this.uploadAddress = uploadAddress;
		this.videoId = videoId;
		this.requestId = requestId;
	}
	
	
	
}


