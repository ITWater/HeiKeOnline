package top.aiteyou.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import top.aiteyou.entity.Team;

/**
	*@author :tb
	*@version 2018年3月21日 下午7:12:33
*/

public interface TeamService {
	Team selectOneDetail(@Param("teamId")int teamId);
	
	List<Team> selctTeams(@Param("seasonId") int seasonId);

}


