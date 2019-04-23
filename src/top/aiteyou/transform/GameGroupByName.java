package top.aiteyou.transform;

import java.io.Serializable;
import java.util.List;

import top.aiteyou.entity.Game;

/**
 *  比赛按name分组
	*@author :tb
	*@version 2018年4月15日 下午1:30:16
*/

public class GameGroupByName  implements Serializable{
	private static final long serialVersionUID = -8808134760122669968L;

	private String gameName;
	
	private List<Game> games;

	public GameGroupByName(String gameName2, List<Game> list) {
		this.gameName=gameName2;
		this.games=list;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	
}


