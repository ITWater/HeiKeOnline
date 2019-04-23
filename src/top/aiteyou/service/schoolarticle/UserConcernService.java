package top.aiteyou.service.schoolarticle;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.schoolArticle.UserConcernInfo;
import top.aiteyou.mapper.schoolarticle.UserConcernInfoMapper;

/**
 * @author :tb
 * @version 2018年4月21日 上午9:50:15
 */
@Service
public class UserConcernService {
	@Autowired
	private UserConcernInfoMapper userConcernMapper = null;

	@Async
	public void concernUser(UserConcernInfo userConcernInfo) {
		userConcernInfo.setTime(new Timestamp(System.currentTimeMillis()));
		userConcernMapper.insertConcern(userConcernInfo);
	}
	@Async
	public void cancelConcernUser(UserConcernInfo userConcernInfo) {
		userConcernMapper.deleteConcern(userConcernInfo.getUserId(), userConcernInfo.getConcernUserId());
	}
}
