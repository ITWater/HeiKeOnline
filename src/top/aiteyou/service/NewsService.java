package top.aiteyou.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import top.aiteyou.entity.News;
import top.aiteyou.transform.NewsLike;

/**
	*@author :tb
	*@version 2018年3月22日 上午11:49:04
*/

public interface NewsService {
	public int insertOneNews(News news);

	public List<News> selectNewsJoinAuthor(RowBounds rowBounds);

	public News selectOneNewsDetail(int newsId);

	public List<News> selectTopJoinAuthor(RowBounds rowBounds);

	public void addNewsCommentCount(int newsId);

	public int addNewsLikeCount(NewsLike newsLike);

	public List<News> selectBanner();

	public void addNewsViewCount(int newsId);
}


