package top.aiteyou.service;

import top.aiteyou.entity.GameProgressStatus;
import top.aiteyou.transform.GameDuiZhen;

/**
	*@author :tb
	*@version 2018年4月15日 下午2:27:06
*/

public interface GameDuiZhenService {


	GameProgressStatus judgeGameStatus(Integer gameId);

	GameDuiZhen getDuiZhenByGameProgressStatus(GameProgressStatus gameProgressStatus);

}


