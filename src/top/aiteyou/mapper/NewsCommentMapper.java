package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.NewsComment;
import top.aiteyou.transform.NewsCommentLike;

/**
	*@author :tb
	*@version 2018年3月24日 下午1:03:25
*/
@Repository
public interface NewsCommentMapper {
	List<NewsComment> selecNewsCommentJoinUser(@Param("newsId")int newsId);
	
	int insertSelective(NewsComment newsComment);

	int addNewsLikeCount(NewsCommentLike newsComment);

	List<NewsComment> selectNotLegalComments(@Param("newsId")int newsId);
}


