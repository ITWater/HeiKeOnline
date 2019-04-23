package top.aiteyou.transform;

import java.io.Serializable;
import java.util.List;

import top.aiteyou.entity.ScoreRanking;

/**
 * 	小组赛积分榜的按小组名分层
	*@author :tb
	*@version 2018年4月9日 下午1:30:06
*/

public class ScoreRankingGroup implements Serializable{

	private static final long serialVersionUID = 6723636478010368606L;

	private String groupName;
	
	private List<ScoreRanking> scoreRankings;
	
	
	
	
	public String getGroupName() {
		return groupName;
	}




	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}




	public List<ScoreRanking> getScoreRankings() {
		return scoreRankings;
	}




	public void setScoreRankings(List<ScoreRanking> scoreRankings) {
		this.scoreRankings = scoreRankings;
	}




	public ScoreRankingGroup(String groupName2, List<ScoreRanking> list) {
		this.groupName=groupName2;
		this.scoreRankings=list;
	}
}


