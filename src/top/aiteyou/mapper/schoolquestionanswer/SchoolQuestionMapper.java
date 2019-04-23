package top.aiteyou.mapper.schoolquestionanswer;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolquestionanswer.SchoolQuestion;

/**
	*@author :tb
	*@version 2018年4月16日 下午2:41:25
*/
@Repository
public interface SchoolQuestionMapper {
	
	public SchoolQuestion selectDetail(@Param("questionId")Integer questionId);

	public List<SchoolQuestion> selectListByQuestionType(@Param("type")String type, RowBounds rowBounds);
	public List<SchoolQuestion> selectListByTopHot(RowBounds rowBounds);
	public List<SchoolQuestion> selectListByForNewest(RowBounds rowBounds);
	public List<SchoolQuestion> selectListByNoPerfectAnswer(RowBounds rowBounds);
}


