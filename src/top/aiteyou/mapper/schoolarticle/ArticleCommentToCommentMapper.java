package top.aiteyou.mapper.schoolarticle;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;
import top.aiteyou.transform.schoolarticle.ArticleCommentToCommentLike;

/**
	*@author :tb
	*@version 2018年4月21日 下午9:50:05
*/
@Repository
public interface ArticleCommentToCommentMapper {
	List<ArticleCommentToComment> selectTopOneJoinUser(@Param("commentId")Integer commentId);
	List<ArticleCommentToComment> selectComments(@Param("articleCommentId")Integer id);
	Integer insertOne(ArticleCommentToComment commentToComment);
	int addLikeCount(ArticleCommentToCommentLike like);
	List<ArticleCommentToComment> selectNotLegalComments(@Param("commentId")Integer commentId);
}


