package top.aiteyou.entity.schoolArticle;

import java.sql.Timestamp;

/**
 *  校园用户关注
	*@author :tb
	*@version 2018年4月20日 下午9:37:52
*/

public class UserConcernInfo {
	private Integer id;
	
	private Integer userId;
	
	private Integer concernUserId;
	
	private Timestamp time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConcernUserId() {
		return concernUserId;
	}

	public void setConcernUserId(Integer concernUserId) {
		this.concernUserId = concernUserId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
	
}


