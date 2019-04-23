package top.aiteyou.entity;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年4月15日 上午11:58:40
*/

public class GameProgressStatus implements Serializable{
	

	private static final long serialVersionUID = 4890562905172546318L;


	private Integer gameId;//主键
	
	private GameProgressStatusEnum status;
		
	public static enum GameProgressStatusEnum{
		小组赛,淘汰赛
	}

	
	
	public GameProgressStatusEnum getStatus() {
		return status;
	}

	public void setStatus(GameProgressStatusEnum status) {
		this.status = status;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	
	
}


