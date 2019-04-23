package top.aiteyou.controller;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.Team;
import top.aiteyou.service.TeamService;

/**
	*@author :tb
	*@version 2018年3月21日 下午12:25:40
*/
@Controller
@RequestMapping(value="/teams")
public class TeamController {
	@Autowired
	private TeamService teamService=null;
	/**
	 * 球队大厅 
	 * 		Team baseData
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public List<Team> selectTeams(@Param("seasonId")int seasonId){
		return teamService.selctTeams(seasonId);
	}
	/**
	 * 球队详情
	 * Team baseData 
	 * 				Game base
	 * 				Members baseData
	 * @param teamId
	 * @return
	 */
	private static String SUCCESS="ok";
	private static String DEFEAT="defeat";
	@RequestMapping(value="/{id}",method=RequestMethod.GET )
	@ResponseBody
	public Team selectOne(@PathVariable("id")int teamId){
		return teamService.selectOneDetail(teamId);
	}
}


