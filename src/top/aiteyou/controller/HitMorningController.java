package top.aiteyou.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.service.HitMorningService;
import top.aiteyou.transform.HitMorningContext;
import top.aiteyou.transform.UserSign;

/**
 * 	打晨卡
	*@author :tb
	*@version 2018年4月8日 下午12:05:54
*/

@Controller
@RequestMapping(value="/hitmorning")
public class HitMorningController {
	
	@Autowired
	private HitMorningService hitMorningService=null;
	/**
	 * 签到
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public HitMorningContext morningSignIn(@PathVariable Integer userId){
		return hitMorningService.morningSignIn(userId);
	}
	/**
	 * 获得每日排行榜
	 * @return
	 */
	@RequestMapping(value="/ranking",method=RequestMethod.GET)
	@ResponseBody
	public List<UserSign> getRanking(){
		return hitMorningService.getRanking();
	}
	
}


