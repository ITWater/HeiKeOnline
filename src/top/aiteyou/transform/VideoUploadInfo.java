package top.aiteyou.transform;

/**
 *  视频上传时签名时需要的信息
	*@author :tb
	*@version 2018年4月12日 下午3:07:52
*/

public class VideoUploadInfo {
	private String fileName;
	
	private String title;
	
	private Integer cateId;
	
	private String tags;
	
	private String decription;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}
	
	
	
}


