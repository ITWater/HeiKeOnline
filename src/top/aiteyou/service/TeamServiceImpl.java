package top.aiteyou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.Team;
import top.aiteyou.mapper.TeamMapper;

/**
	*@author :tb
	*@version 2018年3月21日 下午7:11:59
*/
@Service
public class TeamServiceImpl implements TeamService{
	@Autowired
	private TeamMapper teamMapper=null; 
	
	@Cacheable(value="teams_detail" ,key="'team_id' + #teamId")
	@Override
	public Team selectOneDetail(int teamId) {
		return teamMapper.selectOneJoinGamesMembers(teamId);
	}
	@Cacheable(value="teams_season",key="'season_id' + #seasonId")
	@Override
	public List<Team> selctTeams(int seasonId) {
		return teamMapper.selctTeamsbrief(seasonId);
		
	}
	

}


