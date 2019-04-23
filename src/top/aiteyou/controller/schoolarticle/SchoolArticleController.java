package top.aiteyou.controller.schoolarticle;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.service.JudgeUserIsOnDisableSendMsgService;
import top.aiteyou.service.schoolarticle.ArticleService;
import top.aiteyou.transform.ArticleCommentSelect;
import top.aiteyou.transform.ArticleSelect;
import top.aiteyou.transform.DisableInfo;
import top.aiteyou.transform.schoolarticle.ArticleLike;
import top.aiteyou.websocket.AdminMsgOfNewArticleWebSocketService;

/**
 *  校园动态
	*@author :tb
	*@version 2018年4月11日 上午10:47:43
*/
@Controller
@RequestMapping(value="/schoolarticle/articles")
public class SchoolArticleController {
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private ArticleService  articleService=null;
	@Autowired
	private ArticleCommentMapper articleCommentMapper=null;
	@Autowired
	private JudgeUserIsOnDisableSendMsgService judgeUserIsDisableSendMsg=null;
	@Autowired
	private AdminMsgOfNewArticleWebSocketService adminMsgWebSocketService=null;
	
	private static final String KindOf="NewArticle";
	
	
	/**
	 * 异步添加浏览数
	 */
	@RequestMapping(value="/addViewCount/{id}",method=RequestMethod.GET)
	@ResponseBody
	public int addArticleViewCount(@PathVariable("id") Integer articleId){
		//异步更新浏览数
		articleService.updateArticleViewCount(articleId);
		return 1;
	}
	
	
	
	/**
	 * 取消动态显示
	 * @return
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	@ResponseBody
	private int cancleArticleShow(@PathVariable("id")Integer articleId){
		return articleService.cancleArticleShow(articleId);
	}
	
	/**
	 * 为动态点赞 /取消点赞
	 * @param articleId
	 * @return 0/1
	 */
	@RequestMapping(value="/addlike/{id}",method=RequestMethod.POST)
	@ResponseBody
	public int addArticleLike(@RequestBody ArticleLike articleLike ){
		return articleService.addArticleLike(articleLike);
	}
	
	
	/**
	 * 动态列表
	 * @param articleSelect
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public List<Article> getRecommendArticles(@RequestBody ArticleSelect articleSelect){
		return articleService.selectArticles(articleSelect);
	}
	
	
	/**
	 * 获取动态详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Article getArticleDetail(@PathVariable("id")Integer id){
		Article article=articleService.selectOneDetail(id);
		//异步更新浏览数
		articleService.updateArticleViewCount(id);
		return article;
	}
	/**
	 * 获取更多评论 不适合用缓存 
	 * @param articleCommentSelect
	 * @return
	 */
	@RequestMapping(value="/articlecomments/{articleId}",method=RequestMethod.POST)
	@ResponseBody
	public List<ArticleComment> getMoreArticleComments(@RequestBody ArticleCommentSelect articleCommentSelect){
		int articleId=articleCommentSelect.getArticleId();
		RowBounds rowBounds=new RowBounds(articleCommentSelect.getOffset(), articleCommentSelect.getLimit());
		return articleCommentMapper.getOtherCommentsByArticleId(articleId, rowBounds);
	}
	/**
	 * 添加动态
	 * @param article
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Article insertArticle(@RequestBody Article article){
//		User user=userMapper.seltctVipLevel(article.getUserId());
//		int result=-1;
//		//默认审核通过
//		if (user.getVipLevel()==4 || user.getVipLevel() <=2) {
//			result=articleService.insert(article);
//		}
//		//默认审核不通过
//		else {
//			result=articleService.insertBlock(article);
//			if (result==1) {
//				result=2;//告诉用户等待审核
//			}
//		}
		int userId=article.getUserId();
		// 如果用户禁言中
		DisableInfo disableInfo=judgeUserIsDisableSendMsg.judgeUserIsDisableSendMsg(userId);
		if (disableInfo.isDisable()) {
			article.setStatus(2);
			StringBuffer content=new StringBuffer();
			content.append(DisableInfo.DisableText);
			content.append("距离解封时间还有"+disableInfo.getDiffTime());
			article.setText(content.toString());
			return article;
		}
		User user=userMapper.selectBaseData(userId);
		article.setUser(user);
		 articleService.insert(article);
		 
		 //异步 socket 消息发送给管理员 （所有的新动态)
		 AdminMsg adminMsg=new AdminMsg(userId, article.getText(), article.getTime(), KindOf,article.getImgurls_str());
		 adminMsgWebSocketService.saveAndSendAdminMsgOfNotLegalComment(adminMsg);
		 
		 return article;
	}
	
}


