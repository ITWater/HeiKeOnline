package top.aiteyou.mapper;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.FeedBack;

/**
	*@author :tb
	*@version 2018年4月2日 下午8:46:33
*/
@Repository
public interface FeedBackMapper {
	int insertSelective(FeedBack feedBack);

	int replyFeedBack(FeedBack feedBack);
}


