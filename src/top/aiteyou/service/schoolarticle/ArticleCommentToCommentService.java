package top.aiteyou.service.schoolarticle;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.entity.schoolArticle.ArticleCommentToComment;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentToCommentMapper;
import top.aiteyou.service.AdminMsgTemplateService;
import top.aiteyou.transform.schoolarticle.ArticleCommentToCommentLike;

/**
	*@author :tb
	*@version 2018年4月22日 下午2:30:55
*/
@Service
public class ArticleCommentToCommentService {
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	@Autowired
	private ArticleCommentToCommentMapper commentToCommentMapper=null;
	@Autowired
	private AdminMsgTemplateService adminMsgTemplateService=null;
	/**
	 * 添加一条合法评论的评论  删除动态详情的的缓存 
	 * @param commentToComment
	 * @return
	 */
	@CacheEvict(value="article_detail",key="'articleId'+#articleId")
	public Integer insertOneLegal(Integer articleId,ArticleCommentToComment commentToComment){
		Integer result=commentToCommentMapper.insertOne(commentToComment);
		return result;
	}
	
	/**
	 *  添加一条非法评论的评论  
	 * @param commentToComment
	 * @return
	 */
	public void insertOneNotLegal(ArticleCommentToComment commentToComment){
		commentToCommentMapper.insertOne(commentToComment);
	}
	
	/**
	 * 异步更新动态评论的评论数
	 */
	@Async
	public void updateArticleCommentcommentCount(Integer articleCommentId){
		articleCommentMapper.updataCommentCountById(articleCommentId);
	}
	/**
	 * 评论的评论点赞  清除动态评论的详情缓存
	 * @param like
	 * @return
	 */
	@CacheEvict(value="schoolarticlecomment_detail",key="'articleCommentId_' +#like.commentId")
	public int addLikeCount(ArticleCommentToCommentLike like) {
		return commentToCommentMapper.addLikeCount(like);
	}
	@Async
	public void checkNotLegalCountAndSend(int appId,Integer commentId) {
		List<ArticleCommentToComment> legalComments=commentToCommentMapper.selectNotLegalComments(commentId);
		if (legalComments.size() >=3) {// 如果这个评论已经有超过3条非法评论了 需要模板消息通知管理员
			StringBuffer content=new StringBuffer();
			
			ArticleComment articleComment=articleCommentMapper.selectBaseData(commentId);
			content.append("校园动态的评论"+" "+articleComment.getText()+"   "+GameController.mouthhourFormat.format(articleComment.getTime()));
			content.append("\r\n");
			for (ArticleCommentToComment articleCommentToComment: legalComments) {
				StringBuffer content_item=new StringBuffer();
				String userName=articleCommentToComment.getUser().getName();
				
				String time_str=GameController.hourFormat.format(articleCommentToComment.getTime());
				
				content_item.append(userName+"     "+time_str+"\r\n");
				content_item.append(articleCommentToComment.getContent());
				content_item.append("\r\n");
				content.append(content_item.toString());
			}
			//异步  发送管理员消息 -模板消息
			AdminMsg adminMsg=new AdminMsg(content.toString(), new Timestamp(System.currentTimeMillis()));
			adminMsgTemplateService.sendAdminMsg(appId,adminMsg);
		}
	}

}


