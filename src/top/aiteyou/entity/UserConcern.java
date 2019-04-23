package top.aiteyou.entity;

/**
	*@author :tb
	*@version 2018年4月2日 上午11:35:31
*/

public class UserConcern {
	private Integer id;
	
	private Integer userId;
	
	private Integer gameId;//关注的种类

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

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	
}


