package top.aiteyou.service.schoolarticle;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.Game;
import top.aiteyou.entity.GameComment;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.mapper.schoolarticle.ArticleMapper;
import top.aiteyou.service.AdminMsgTemplateService;
import top.aiteyou.textscan.TextAntispamScanSample;
import top.aiteyou.transform.schoolarticle.ArticleCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;

/**
	*@author :tb
	*@version 2018年4月20日 下午6:38:44
*/
@Service
public class ArticleCommentService {
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	@Autowired
	private ArticleMapper articleMapper=null;
	@Autowired
	private AdminMsgTemplateService adminMsgTemplateService=null;
	
	/**
	 * 获取某校园动态评论的详情
	 * @param articleCommentId
	 * @return
	 */
	@Cacheable(value="schoolarticlecomment_detail",key="'articleCommentId_' +#articleCommentId")
	public ArticleComment getOneDetail(Integer articleCommentId){
		return articleCommentMapper.selectOneDetailAndComments(articleCommentId); 
	}
	
	/**
	 * 添加合法评论 同步 清动态详情缓存
	 * @param articleComment
	 * @return
	 */
	@CacheEvict(value="article_detail",key="'articleId'+#articleComment.articleId")
	public int insertComment(ArticleComment articleComment){
		return articleCommentMapper.insertOneComment(articleComment);
	}
	/**
	 * 添加非法评论   不清缓存
	 * @param articleComment
	 */
	public void insertBlockNewsComment(ArticleComment articleComment) {
		articleCommentMapper.insertOneComment(articleComment);
	}
	/**
	 * 异步更新动态的评论数 删除动态详情缓存
	 * @param articleId
	 */
	@Async
	@CacheEvict(value="article_detail",key="'articleId'+#articleId")
	public void updateArticleCommentCount(int articleId) {
		articleMapper.updateCommentCount(articleId);
	}
	/**
	 * 校园评论点赞 清除动态详情缓存
	 * @param commentLike
	 * @return
	 */
	@CacheEvict(value="article_detail",key="'articleId'+#commentLike.articleId")
	public int addLikeCount(ArticleCommentLike commentLike) {
		return articleCommentMapper.addLikeCount(commentLike);
	}
	/**
	 * 检查某动态的脏评论>=3 尝试发送管理员消息给所有管理员
	 * @param articleId
	 */
	@Async
	public void checkNotLegalCountAndSend(int appId,Integer articleId) {
		List<ArticleComment> legalComments=articleCommentMapper.selectNotLegalComments(articleId);
		if (legalComments.size() >=3) {// 如果这场赛事已经有超过3条非法评论了 需要模板消息通知管理员
			StringBuffer content=new StringBuffer();
			
			Article article=articleMapper.selectBaseData(articleId);
			content.append("校园动态"+" "+article.getText()+"   "+GameController.mouthhourFormat.format(article.getTime()));
			content.append("\r\n");
			for (ArticleComment articleComment: legalComments) {
				StringBuffer content_item=new StringBuffer();
				String userName=articleComment.getUser().getName();
				
				String time_str=GameController.hourFormat.format(articleComment.getTime());
				
				content_item.append(userName+"     "+time_str+"\r\n");
				content_item.append(articleComment.getText());
				content_item.append("\r\n");
				content.append(content_item.toString());
			}
			//异步  发送管理员消息 -模板消息
			AdminMsg adminMsg=new AdminMsg(content.toString(), new Timestamp(System.currentTimeMillis()));
			adminMsgTemplateService.sendAdminMsg(appId,adminMsg);
		}
	}
	/**
	 * 取消某动态的评论 显示 清除 动态的详情缓存 
	 * @param articleCommentId
	 * @return
	 */
	@CacheEvict(value="article_detail",key="'articleId'+#articleComment.articleId")
	public int cancleCommentShow(ArticleComment articleComment) {
		return articleCommentMapper.updateCommentStatus(articleComment.getId());
	}
}


