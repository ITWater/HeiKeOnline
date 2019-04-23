package top.aiteyou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.ShooterRanking;
import top.aiteyou.service.ShooterRankingService;

/**
 *  射手榜
	*@author :tb
	*@version 2018年4月8日 上午9:38:59
*/
@Controller
@RequestMapping(value="/shooterrankings")
public class ShooterRankingController {
	
	@Autowired
	private ShooterRankingService shootRankingService=null;
	
	@RequestMapping(value="/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public List<ShooterRanking> select(@PathVariable("gameId") Integer gameId){
		if (gameId==null) {
			gameId=0;//默认足球联赛的射手榜
		}
		return shootRankingService.select(gameId);
	}
	
}


