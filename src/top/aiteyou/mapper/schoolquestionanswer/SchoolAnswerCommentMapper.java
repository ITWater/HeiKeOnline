package top.aiteyou.mapper.schoolquestionanswer;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolquestionanswer.SchoolAnswerComment;

/**
	*@author :tb
	*@version 2018年4月16日 下午2:20:12
*/
@Repository
public interface SchoolAnswerCommentMapper {
	public List<SchoolAnswerComment> selectByAnswerId(@Param("answerId") Integer answerId);
}


