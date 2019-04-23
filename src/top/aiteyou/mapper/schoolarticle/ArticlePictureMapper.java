package top.aiteyou.mapper.schoolarticle;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolArticle.ArticlePicture;

/**
	*@author :tb
	*@version 2018年4月11日 下午1:35:29
*/
@Repository
public interface ArticlePictureMapper {
	/**
	 * 查询某动态里的图片s
	 * @param articleId
	 * @return
	 */
	List<ArticlePicture> selectPicturesByArticleId(@Param("articleId")int articleId);

	int insetBatch(List<ArticlePicture> articlePictures);
}


