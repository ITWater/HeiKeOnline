package top.aiteyou.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.aiteyou.entity.News;
import top.aiteyou.mapper.NewsMapper;
import top.aiteyou.transform.NewsLike;
import top.aiteyou.transform.NewsView;

/**
	*@author :tb
	*@version 2018年3月22日 上午11:50:02
	*Cache: news_brief用于首页 资讯查询
*/
@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsMapper newsMapper=null;
	
	@Async
	@Override
	public void addNewsViewCount(int newsId) {
		NewsView newsView=new NewsView(newsId,1);
		newsMapper.addNewsViewCount(newsView);
	}
	
	
	@CachePut(value="news_brief",key="'news_'+#news.id")
	@Override
	public int insertOneNews(News news) {
		return newsMapper.insertOneNews(news);
	}
	
	@Cacheable(value="news_brief",key="'news_brief_offect_' + #rowBounds.offset +'limit'+ #rowBounds.limit ")
	@Override
	public List<News> selectNewsJoinAuthor(RowBounds rowBounds) {
		return newsMapper.selectNewsJoinAuthor(rowBounds);
	}
	
	@Cacheable(value="news_detail",key="'news_detail' + #newsId")
	@Override
	public News selectOneNewsDetail(int newsId) {
		return newsMapper.selectNewsJoinAuthorComments(newsId);
	}

	@Cacheable(value="news_top",key="'topnews_brief_offect_' + #rowBounds.offset +'limit'+ #rowBounds.limit ")
	@Override
	public List<News> selectTopJoinAuthor(RowBounds rowBounds) {
		return newsMapper.selectTopNews(rowBounds);
	}
	/**
	 * 因为请求不需要此处结果，所以使用异步线程提高效率
	 * 删除资讯详情缓存
	 */
	@Async
	@CacheEvict(value="news_detail",key="'news_detail' + #newsId")
	@Override
	public void addNewsCommentCount(int newsId) {
		 newsMapper.addNewsCommentCount(newsId);
	}
	@CacheEvict(value="news_detail",key="'news_detail' + #newsLike.newsId")
	@Override
	public int addNewsLikeCount(NewsLike newsLike) {
		return newsMapper.addNewsLikeCount(newsLike);
	}

	@Cacheable(value="banner",key="'banners'")
	@Override
	public List<News> selectBanner() {
		return newsMapper.selectTopBanners();
	}
	
	

}


