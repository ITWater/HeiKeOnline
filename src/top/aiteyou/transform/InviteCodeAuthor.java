package top.aiteyou.transform;

/**
 *  邀请码生成需要的权限
	*@author :tb
	*@version 2018年4月26日 上午11:45:10
*/

public class InviteCodeAuthor {
	private Integer userId;
	
	private String password;
	
	private Integer generateCount;

	private Integer kindof;//生成邀请码种类
	
	
	
	public InviteCodeAuthor() {
		this.userId=-1;
		this.password="-1";
		generateCount=0;
		kindof=-1;
	}

	public Integer getKindof() {
		return kindof;
	}

	public void setKindof(Integer kindof) {
		this.kindof = kindof;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGenerateCount() {
		return generateCount;
	}

	public void setGenerateCount(Integer generateCount) {
		this.generateCount = generateCount;
	}
	
	
}


