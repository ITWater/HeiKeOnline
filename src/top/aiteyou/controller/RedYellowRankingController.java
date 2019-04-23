package top.aiteyou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.RedYellowRanking;
import top.aiteyou.service.RedYellowRankingService;

/**	红黄牌榜
	*@author :tb
	*@version 2018年4月8日 上午11:33:20
*/
@Controller
@RequestMapping(value="/redyellowrankings")
public class RedYellowRankingController {
	
	@Autowired
	private RedYellowRankingService redYellowRankingService=null; 
	
	@RequestMapping(value="/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public List<RedYellowRanking> select(@PathVariable("gameId") Integer gameId){
		if (gameId==null) {
			gameId=0;
		}
		return redYellowRankingService.select(gameId); 
	} 
}


