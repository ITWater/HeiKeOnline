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
import top.aiteyou.entity.User;
import top.aiteyou.mapper.GameCommentMapper;
import top.aiteyou.mapper.GameMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.GameCommentLike;

/**
 * 	评论业务
	*@author :tb
	*@version 2018年3月18日 下午3:46:36
*/

@Service
	public class GameCommentServiceImpl implements GameCommentService {
	@Autowired
	private GameCommentMapper gameCommentMapper=null;
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private AdminMsgTemplateService adminMsgTemplateService=null;
	@Autowired
	private GameMapper gameMapper=null;
	
	/**
	 * 添加比赛评论，刷新 比赛详情的的缓存
	 * @param gameComment
	 * @return
	 */
	@CacheEvict(value="games_detail",key="'game_id' + #gameComment.gameId")
	@Override
	public GameComment insertGameCommentJoinUser(GameComment gameComment) {
		 gameCommentMapper.insertSelective(gameComment);
		 User user=userMapper.selectByPrimaryKey(gameComment.getUserId());
		 gameComment.setUser(user);
		 return gameComment;
	}
	/**
	 * 点赞 比赛评论，并删除比赛详情的缓存
	 */
	@Override
	@CacheEvict(value="games_detail",key="'game_id' + #gameComment.gameId")
	public int addGameCommentLikeCount(GameCommentLike gameComment) {
		return gameCommentMapper.addLikeCount(gameComment);
	}
	/**
	 * 添加怀疑内容的赛事评论,不清除缓存
	 */
	@Override
	public GameComment insertBlockNewsComment(GameComment gameComment) {
		gameCommentMapper.insertSelective(gameComment);
		 User user=userMapper.selectByPrimaryKey(gameComment.getUserId());
		 gameComment.setUser(user);
		 return gameComment;
	}
	@Async
	@Override
	public void checkNotLegalCountAndSend(int appId,Integer gameId) {
		List<GameComment> legalComments=gameCommentMapper.selectNotLegalComments(gameId);
		if (legalComments.size() >=3) {// 如果这场赛事已经有超过3条非法评论了 需要模板消息通知管理员
			StringBuffer content=new StringBuffer();
			
			Game game=gameMapper.selectOneBaseInfo(gameId);
			String gameKind="";
			switch (game.getGameId()) {
			case 0:
				gameKind="足球联赛";
				break;
			case 1:
				gameKind="足球杯赛";
				break;
			case 2:
				gameKind="火神杯男篮";
				break;
			case 3:
				gameKind="火神杯女篮";
				break;
			}
			content.append(gameKind+" "+game.getTeam1().getName() +" VS "+game.getTeam2().getName() +"   "+GameController.mouthhourFormat.format(game.getTime()));
			content.append("\r\n");
			for (GameComment gameComment: legalComments) {
				StringBuffer content_item=new StringBuffer();
				String userName=gameComment.getUser().getName();
				
				String time_str=GameController.hourFormat.format(gameComment.getTime());
				
				content_item.append(userName+"     "+time_str+"\r\n");
				content_item.append(gameComment.getContent());
				content_item.append("\r\n");
				content.append(content_item.toString());
			}
			//异步  发送管理员消息
			AdminMsg adminMsg=new AdminMsg(content.toString(), new Timestamp(System.currentTimeMillis()));
			adminMsgTemplateService.sendAdminMsg(appId,adminMsg);
			
		}
		
	}
	
}


