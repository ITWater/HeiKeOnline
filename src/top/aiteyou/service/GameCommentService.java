package top.aiteyou.service;

import top.aiteyou.entity.GameComment;
import top.aiteyou.transform.GameCommentLike;

/**
	*@author :tb
	*@version 2018年3月28日 上午10:51:57
*/

public interface GameCommentService {

	GameComment insertGameCommentJoinUser(GameComment gameComment);


	int addGameCommentLikeCount(GameCommentLike gameComment);


	GameComment insertBlockNewsComment(GameComment gameComment);


	void checkNotLegalCountAndSend(int appId,Integer integer);

}


