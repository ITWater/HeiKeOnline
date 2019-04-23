package top.aiteyou.mapper.schoolquestionanswer;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.schoolquestionanswer.SchoolQuestionLabel;

/**
	*@author :tb
	*@version 2018年4月16日 下午9:07:13
*/
@Repository
public interface SchoolQuestionLabelMapper {
	public List<SchoolQuestionLabel> selectAll();
}


