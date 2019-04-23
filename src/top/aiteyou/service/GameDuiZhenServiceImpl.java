package top.aiteyou.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.Game;
import top.aiteyou.entity.GameProgressStatus;
import top.aiteyou.entity.ScoreRanking;
import top.aiteyou.entity.GameProgressStatus.GameProgressStatusEnum;
import top.aiteyou.mapper.GameMapper;
import top.aiteyou.mapper.GameProgressStatusMapper;
import top.aiteyou.mapper.ScoreRankingMapper;
import top.aiteyou.transform.GameDuiZhen;
import top.aiteyou.transform.GameGroupByName;
import top.aiteyou.transform.ScoreRankingGroup;

/**
	*@author :tb
	*@version 2018年4月15日 下午12:35:18
*/
@Service
public class GameDuiZhenServiceImpl implements GameDuiZhenService{
	@Autowired
	private GameProgressStatusMapper gameProgressStatusMapper=null;
	@Autowired
	private ScoreRankingMapper scoreRankingMapper=null;
	@Autowired
	private GameMapper gameMapper=null;
	
	/**
	 * 获取某种比赛的对阵信息
	 * 
	 * @param gameProgressStatus 包含gameId 和 比赛进行状态
	 * @return
	 */
	@Override
	@Cacheable(value="gameduizhen",key="'gameId_'+#gameProgressStatus.gameId + 'status_'+#gameProgressStatus.status")
	public  GameDuiZhen getDuiZhenByGameProgressStatus(GameProgressStatus gameProgressStatus){
		GameDuiZhen gameDuiZhen=null;
		int gameId=gameProgressStatus.getGameId();
		//判断比赛状态
			if (gameProgressStatus.getStatus()== GameProgressStatusEnum.小组赛) {
				List<ScoreRanking> scoreRankings=scoreRankingMapper.selectByGameIdGroup(gameId);
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
				gameDuiZhen=new GameDuiZhen(gameProgressStatus, new ArrayList<>(map.values()));
			}
			else if (gameProgressStatus.getStatus()== GameProgressStatusEnum.淘汰赛) {
				//不要小组赛数据
				List<Game> games=gameMapper.selectAllGameByGameIdOrderByTimeDesc(gameId);
				Map<String,GameGroupByName> map=new LinkedHashMap<>();
				for (Game game : games) {
					String time_str=GameController.mouthhourFormat.format(game.getTime());
					game.setTime_str(time_str);
					String gameName=game.getName();
					if (!map.containsKey(gameName)) {
						List<Game> list=new ArrayList<>();
						GameGroupByName gameGroupByName=new GameGroupByName(gameName,list);
						map.put(gameName, gameGroupByName);
					}
					map.get(gameName).getGames().add(game);
				}
				//获取淘汰赛对阵图
				String imgUrl=null;
				
				if (gameId==1) {//足球杯赛的对阵图
				    imgUrl="https://heikeonline.oss-cn-beijing.aliyuncs.com/gameImage/gameId1taotaisaiduizhentu.jpg";
				}else if (gameId==2) {
					imgUrl="https://heikeonline.oss-cn-beijing.aliyuncs.com/gameImage/gameId2taotaisaiduizhentu.jpg";
				}else if (gameId==3) {
					imgUrl="https://heikeonline.oss-cn-beijing.aliyuncs.com/gameImage/gameId3taotaisaiduizhentu.jpg";
				}
					
				gameDuiZhen=new GameDuiZhen(gameProgressStatus, imgUrl,new ArrayList<>(map.values()));
			}
		return gameDuiZhen;
	}
	
	
	@Override
	@Cacheable(value="game_status",key="'game' + #gameId")
	public  GameProgressStatus judgeGameStatus(Integer gameId){
		return gameProgressStatusMapper.selectByPrimaryKey(gameId);
	}

}


