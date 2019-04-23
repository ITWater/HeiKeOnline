package top.aiteyou.mapper.schoolquestionanswer;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolquestionanswer.SchoolAnswer;

/**
	*@author :tb
	*@version 2018年4月16日 下午2:33:30
*/
@Repository
public interface SchoolAnswerMapper {
	List<SchoolAnswer> selectByQuestionId(@Param("questionId")Integer questionId,RowBounds rowBounds);
}


