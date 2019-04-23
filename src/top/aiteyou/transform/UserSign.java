package top.aiteyou.transform;

import java.io.Serializable;
import java.sql.Timestamp;

import top.aiteyou.entity.User;

/**
 * 用户签到
	*@author :tb
	*@version 2018年4月26日 下午5:00:27
*/

public class UserSign implements Serializable{
	private static final long serialVersionUID = 6604579257659357585L;

	private User user;
	
	private Timestamp time;

	public UserSign(User user2, Timestamp timestamp) {
		this.user=user2;
		this.time=timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
	
}


