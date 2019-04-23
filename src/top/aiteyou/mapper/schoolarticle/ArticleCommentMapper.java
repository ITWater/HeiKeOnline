package top.aiteyou.mapper.schoolarticle;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.transform.schoolarticle.ArticleCommentLike;

/**
	*@author :tb
	*@version 2018年4月11日 上午11:54:00
*/
@Repository
public interface ArticleCommentMapper {
	/**
	 * 按照点赞数排序 默认先给10条评论
	 * @param articleId
	 * @return
	 */
	public List<ArticleComment> getArticleCommentByArticleId(@Param("articleId")int articleId);
	/**
	 * 获取更多评论
	 * @param articleId
	 * @param rowBounds
	 * @return
	 */
	public List<ArticleComment> getOtherCommentsByArticleId(@Param("articleId")int articleId,
			RowBounds rowBounds);
	
	public int insertOneComment(ArticleComment articleComment);
	
	public ArticleComment selectOneDetailAndComments(@Param("articleCommentId")Integer articleCommentId);
	
	public ArticleComment selectBaseData(@Param("articleCommentId")int articleCommentId);
	public void updataCommentCountById(@Param("articleCommentId")Integer articleCommentId);
	public int addLikeCount(ArticleCommentLike commentLike);
	public List<ArticleComment> selectNotLegalComments(@Param("articleId")Integer articleId);
	public int updateCommentStatus(@Param("articleId")Integer id);
}


