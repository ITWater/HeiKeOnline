package top.aiteyou.transform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.aiteyou.entity.Game;
import top.aiteyou.entity.GameProgressStatus;
import top.aiteyou.entity.ScoreRanking;

/**
 * 	对阵信息
	*@author :tb
	*@version 2018年4月15日 下午12:25:39
*/

public class GameDuiZhen implements Serializable{
	
	private static final long serialVersionUID = 2306771246185664829L;

	//前端根据.status判断比赛状态
	private GameProgressStatus gameProgressStatus;
	/**
	 * 两种内容
	 */
	//淘汰赛Data
	private String imgUrl;
	private List<GameGroupByName> gameGroups;
	/***   or    ***/
	//小组赛Data
	private List<ScoreRankingGroup> scoreRankingGroups;

	
	
	
	

	public GameDuiZhen(GameProgressStatus gameProgressStatus,List<ScoreRankingGroup> scoreRankingGroups) {
		this.gameProgressStatus=gameProgressStatus;
		this.scoreRankingGroups=scoreRankingGroups;
	}

	

	public GameDuiZhen(GameProgressStatus gameProgressStatus2, String imgUrl2, ArrayList<GameGroupByName> gameGroups2) {
		this.gameProgressStatus=gameProgressStatus2;
		this.imgUrl=imgUrl2;
		this.gameGroups=gameGroups2;
	}



	public GameProgressStatus getGameProgressStatus() {
		return gameProgressStatus;
	}



	public void setGameProgressStatus(GameProgressStatus gameProgressStatus) {
		this.gameProgressStatus = gameProgressStatus;
	}



	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}




	public List<GameGroupByName> getGameGroups() {
		return gameGroups;
	}



	public void setGameGroups(List<GameGroupByName> gameGroups) {
		this.gameGroups = gameGroups;
	}



	public List<ScoreRankingGroup> getScoreRankingGroups() {
		return scoreRankingGroups;
	}



	public void setScoreRankingGroups(List<ScoreRankingGroup> scoreRankingGroups) {
		this.scoreRankingGroups = scoreRankingGroups;
	}

	
	
	
	
	
}


