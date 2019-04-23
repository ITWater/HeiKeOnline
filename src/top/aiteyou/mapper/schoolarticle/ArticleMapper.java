package top.aiteyou.mapper.schoolarticle;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.transform.schoolarticle.ArticleLike;

/**
	*@author :tb
	*@version 2018年4月11日 上午10:59:34
*/
@Repository
public interface ArticleMapper {

	List<Article> selectArticles(@Param("concernIds")List<String> concernIds, RowBounds rowBounds);

	Article selectOneDetail(@Param("id")Integer id);

	int insert(Article article);

	Article selectBaseData(int articleId);

	int updateArticleViewCount(Integer id);

	void updateCommentCount(int articleId);


	int addLikeCount(ArticleLike articleLike);

	int updateArticleStatus(@Param("id")Integer articleId);

}


