package top.aiteyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.GameProgressStatus;
import top.aiteyou.service.GameDuiZhenService;
import top.aiteyou.transform.GameDuiZhen;

/**
	*@author :tb
	*@version 2018年4月15日 下午12:08:01
*/
@Controller
@RequestMapping(value="/contrast")
public class GameDuiZhenController {
	
	@Autowired
	private GameDuiZhenService gameDuiZhenService=null;
	/**
	 * 获取各比赛对阵信息
	 * @param gameId
	 * @return
	 */
	@RequestMapping(value="/{gameId}",method=RequestMethod.GET)
	@ResponseBody
	public GameDuiZhen getDuiZhen(@PathVariable Integer gameId){
		GameProgressStatus gameProgressStatus=gameDuiZhenService.judgeGameStatus(gameId);
		return gameDuiZhenService.getDuiZhenByGameProgressStatus(gameProgressStatus);
		
		
	}
	
	
	

}


