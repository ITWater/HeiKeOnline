package top.aiteyou.transform;

import java.text.SimpleDateFormat;
import java.util.List;

import top.aiteyou.entity.Game;

/**
	*@author :tb
	*@version 2018年3月26日 上午9:59:50
*/

public class GameTimeFormat {
	public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
	
	private String time;
	
	private List<Game> games;


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public GameTimeFormat(String time, List<Game> games) {
		super();
		this.time = time;
		this.games = games;
	}

	public GameTimeFormat() {
	}
	
	
}


