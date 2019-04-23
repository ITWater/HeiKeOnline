package top.aiteyou.transform;

import java.io.Serializable;

/**
 * 禁言信息
	*@author :tb
	*@version 2018年5月6日 下午2:23:14
*/

public class DisableInfo implements Serializable{
	private static final long serialVersionUID = -7301307721280844572L;

	public static final String DisableText="由于你不文明的言论行为,你的账户已被禁言,如果有疑问可以进行反馈.";
	
	private boolean disable;
	
	private String diffTime;

	public boolean isDisable() {
		return disable;
	}

	public DisableInfo(boolean isDisable, String diffTime) {
		super();
		this.disable = isDisable;
		this.diffTime = diffTime;
	}

	public void setDisable(boolean isDisable) {
		this.disable = isDisable;
	}

	public String getDiffTime() {
		return diffTime;
	}

	public void setDiffTime(String diffTime) {
		this.diffTime = diffTime;
	}

	
	
}


