package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.News;
import top.aiteyou.transform.NewsLike;
import top.aiteyou.transform.NewsView;

/**
	*@author :tb
	*@version 2018年3月22日 上午11:32:07
*/
@Repository
public interface NewsMapper {
	/**
	 * 带主键自动生成
	 * @param news
	 * @return
	 */
	int insertOneNews(News news);

	List<News> selectNewsJoinAuthor(RowBounds rowBounds);

	News selectNewsJoinAuthorComments(int newsId);

	List<News> selectTopNews(RowBounds rowBounds);

	int addNewsCommentCount(int newsId);

	int addNewsLikeCount(NewsLike newsLike);

	List<News> selectTopBanners();

	int addNewsViewCount(NewsView newsView);

	News selectOneBaseInfo(int newsId);

	int addViewCount(@Param("newsId")Integer newId);
	
}


