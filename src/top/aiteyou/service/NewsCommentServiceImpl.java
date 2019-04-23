package top.aiteyou.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.AdminMsg;
import top.aiteyou.entity.Game;
import top.aiteyou.entity.GameComment;
import top.aiteyou.entity.News;
import top.aiteyou.entity.NewsComment;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.NewsCommentMapper;
import top.aiteyou.mapper.NewsMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.NewsCommentLike;
import top.aiteyou.websocket.AdminMsgOfNotLegalCommentWebSocketService;

/**
	*@author :tb
	*@version 2018年3月28日 下午4:23:48
*/
@Service
public class NewsCommentServiceImpl implements NewsCommentService{
	@Autowired
	private NewsCommentMapper newsCommentMapper=null;
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private NewsMapper newsMapper=null;
	@Autowired
	private AdminMsgTemplateService adminMsgTemplateService=null;
	
	
	/**
	 * 增加一条资讯评论 并清除详细资讯的缓存
	 */
	@CacheEvict(value="news_detail",key="'news_detail' + #newsComment.newsId")
	@Override
	public int insertNewsComment(NewsComment newsComment) {
		 User user=userMapper.selectBaseData(newsComment.getUserId());
		 newsComment.setUser(user);
		return newsCommentMapper.insertSelective(newsComment);
	}
	/**
	 * 增加一条怀疑资讯评论 ,不需要清除资讯缓存
	 */
	@Override
	public int insertBlockNewsComment(NewsComment newsComment) {
		return newsCommentMapper.insertSelective(newsComment);
	}
	/**
	 * 点赞资讯评论 并清除详细资讯的缓存
	 */
	@Override
	@CacheEvict(value="news_detail",key="'news_detail' + #newsComment.newsId")
	public int addNewsCommentLike(NewsCommentLike newsComment) {
		return newsCommentMapper.addNewsLikeCount(newsComment);
	}
	@Override
	public void checkNotLegalCountAndSend(int appId,int newsId) {
		List<NewsComment> legalComments=newsCommentMapper.selectNotLegalComments(newsId);
		if (legalComments.size() >=3) {// 如果这资讯已经有超过3条非法评论了 需要模板消息通知管理员
			StringBuffer content=new StringBuffer();
			
			News news=newsMapper.selectOneBaseInfo(newsId);
			content.append("资讯"+" "+news.getTitle() +"   "+GameController.mouthhourFormat.format(news.getTime()));
			content.append("\r\n");
			for (NewsComment newsComment: legalComments) {
				StringBuffer content_item=new StringBuffer();
				String userName=newsComment.getUser().getName();
				
				String time_str=GameController.hourFormat.format(newsComment.getTime());
				
				content_item.append(userName+"     "+time_str+"\r\n");
				content_item.append(newsComment.getContent());
				content_item.append("\r\n");
				content.append(content_item.toString());
			}
			//异步  发送管理员消息 模板消息
			AdminMsg adminMsg=new AdminMsg(content.toString(), new Timestamp(System.currentTimeMillis()));
			adminMsgTemplateService.sendAdminMsg(appId,adminMsg);
		}

	}
}


