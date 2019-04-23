package top.aiteyou.transform;

import top.aiteyou.entity.User;

/**
 * 用户信息更新时返回
	*@author :tb
	*@version 2018年4月26日 下午2:51:41
*/

public class UserMsg {
	private Integer result;
	
	private String msg;
	
	private User user;

	public UserMsg(Integer result,String msg2, User user2) {
		this.result=result;
		this.msg=msg2;
		this.user=user2;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}


