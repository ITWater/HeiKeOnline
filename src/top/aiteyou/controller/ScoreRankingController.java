package top.aiteyou.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.ScoreRanking;
import top.aiteyou.service.ScoreRankingService;
import top.aiteyou.transform.ScoreRankingGroup;

/**
 * 	积分榜
	*@author :tb
	*@version 2018年4月8日 上午9:38:59
*/
@Controller
@RequestMapping(value="/scorerankings")
public class ScoreRankingController {
	
	@Autowired
	private ScoreRankingService scoreRankingService=null;
	/**
	 * 小组赛的积分榜
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="/group/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public Collection<ScoreRankingGroup> selectGroup(@PathVariable("gameId") Integer gameId){
		if (gameId==null) {
			gameId=1;
		}
		List<ScoreRanking> scoreRankings=scoreRankingService.selectGroup(gameId);
		Map<String, ScoreRankingGroup> map=new LinkedHashMap<>();
		
		for (ScoreRanking scoreRanking : scoreRankings) {
			String groupName=scoreRanking.getGroupName();
			if (!map.containsKey(groupName)) {
				List<ScoreRanking> list=new ArrayList<>();
				ScoreRankingGroup scoreRankingGroup=new ScoreRankingGroup(groupName,list);
				map.put(groupName,scoreRankingGroup);
			}
			map.get(groupName).getScoreRankings().add(scoreRanking);
		}
		return map.values();
	}
	/**
	 * 联赛的积分榜 groupname==-1
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="/circulation/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public List<ScoreRanking> select(@PathVariable("gameId") Integer gameId){
		if (gameId==null) {
			gameId=0;
		}
		return scoreRankingService.selectCirculation(gameId);
	}
	
}


