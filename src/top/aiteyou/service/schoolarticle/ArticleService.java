package top.aiteyou.service.schoolarticle;

import java.util.List;

import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.transform.ArticleSelect;
import top.aiteyou.transform.schoolarticle.ArticleLike;

/**
	*@author :tb
	*@version 2018年4月11日 上午10:55:29
*/

public interface ArticleService {

	List<Article> selectArticles(ArticleSelect articleSelect);


	Article selectOneDetail(Integer id);


	int insert(Article article);


	void updateArticleViewCount(Integer id);


	int addArticleLike(ArticleLike articleLike);


	int insertBlock(Article article);


	int cancleArticleShow(Integer articleId);

}


