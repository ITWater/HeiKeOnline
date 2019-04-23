package top.aiteyou.service;

import top.aiteyou.entity.NewsComment;
import top.aiteyou.transform.NewsCommentLike;

/**
	*@author :tb
	*@version 2018年3月28日 下午4:19:35
*/

public interface NewsCommentService {

	public int insertNewsComment(NewsComment newsComment) ;

	public int addNewsCommentLike(NewsCommentLike newsComment);

	int insertBlockNewsComment(NewsComment newsComment);

	public void checkNotLegalCountAndSend(int appId,int newsId);
	
}


