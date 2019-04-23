package top.aiteyou.service.schoolarticle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticlePicture;
import top.aiteyou.mapper.schoolarticle.ArticleMapper;
import top.aiteyou.mapper.schoolarticle.ArticlePictureMapper;
import top.aiteyou.transform.ArticleSelect;
import top.aiteyou.transform.schoolarticle.ArticleLike;

/**
	*@author :tb
	*@version 2018年4月11日 上午10:56:23
*/
@Service
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleMapper articleMapper=null;
	@Autowired
	private ArticlePictureMapper articlePictureMapper=null;
	/**
	 * 查询动态 按照发表时间排序 
	 * 1.获取推荐 userConcernIds []=null
	 * 2.获取关注人的 userConcernIds=[,,]
	 */
	@Cacheable(value="articles_brief",key="'articles_brief_offset_' +#articleSelect.offset + 'limit_'+#articleSelect.limit+ 'concernIds'+#articleSelect.concernIds")
	@Override
	public List<Article> selectArticles(ArticleSelect articleSelect) {
		RowBounds rowBounds=new RowBounds(articleSelect.getOffset(), articleSelect.getLimit());
		String [] temp=articleSelect.getConcernIds();
		List<String> userConcernIds=(temp==null?null:Arrays.asList(articleSelect.getConcernIds()));
		return articleMapper.selectArticles(userConcernIds,rowBounds);
	}
	/**
	 * 动态详情 会附带20条评论（likecount desc）
	 */
	@Cacheable(value="article_detail",key="'articleId'+#id")
	@Override
	public Article selectOneDetail(Integer id) {
		return articleMapper.selectOneDetail(id);
	}
	/**
	 * 异步更新动态的浏览数 ，但不清楚缓存
	 * @param id
	 */
	@Async
	@Override
	public void updateArticleViewCount(Integer id){
		articleMapper.updateArticleViewCount(id);
	}
	/**
	 * 添加校园动态 清除所有动态列表查询缓存 社团认证
	 */
	@CacheEvict(value="articles_brief",key="'articles_brief_offset_0'  + 'limit_10'+ 'concernIdsnull'")
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	@Override
	public int insert(Article article) {
		article.setTime(new Timestamp(System.currentTimeMillis()));
		article.setStatus(1);
		int result2=articleMapper.insert(article);
		List<ArticlePicture> articlePictures=new ArrayList<ArticlePicture>();
		String imgurls[]=article.getImgurls_str();
		for(int i=0;i<imgurls.length;i++){
			ArticlePicture articlePicture=new ArticlePicture(article.getId(),imgurls[i]);
			articlePictures.add(articlePicture);
		}
		int result1=articlePictures.size();
		if (articlePictures.size()>0) {
			result1=articlePictureMapper.insetBatch(articlePictures);
		}
		
		return (result1==imgurls.length && result2==1)?1:0;
		
	}
	/**
	 * 添加校园动态  非社团认证
	 */
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED)
	@Override
	public int insertBlock(Article article) {
		article.setTime(new Timestamp(System.currentTimeMillis()));
		article.setStatus(0);
		int result2=articleMapper.insert(article);
		List<ArticlePicture> articlePictures=new ArrayList<ArticlePicture>();
		String imgurls[]=article.getImgurls_str();
		for(int i=0;i<imgurls.length;i++){
			ArticlePicture articlePicture=new ArticlePicture(article.getId(),imgurls[i]);
			articlePictures.add(articlePicture);
		}
		int result1=articlePictures.size();
		if (articlePictures.size()>0) {
			result1=articlePictureMapper.insetBatch(articlePictures);
		}
		
		return (result1==imgurls.length && result2==1)?1:0;
		
	}
	/**
	 * 校园动态点赞 清除动态详情缓存
	 * @param articleId
	 * @return
	 */
	@CacheEvict(value="article_detail",key="'articleId'+#articleLike.articleId")
	@Override
	public int addArticleLike(ArticleLike articleLike) {
		return articleMapper.addLikeCount(articleLike);
	}
	/**
	 * 取消某条动态的显示 ，清除最新动态列表缓存
	 */
	@CacheEvict(value="articles_brief",key="'articles_brief_offset_0'  + 'limit_10'+ 'concernIdsnull'")
	@Override
	public int cancleArticleShow(Integer articleId) {
		return articleMapper.updateArticleStatus(articleId);
	}

}


