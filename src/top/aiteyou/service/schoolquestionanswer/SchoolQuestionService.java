package top.aiteyou.service.schoolquestionanswer;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.schoolquestionanswer.SchoolQuestion;
import top.aiteyou.mapper.schoolquestionanswer.SchoolQuestionMapper;
import top.aiteyou.transform.schoolquestionanswer.SchoolQuestionSelect;
import top.aiteyou.transform.schoolquestionanswer.SchoolQuestionSelect.SchoolQuestionQueryType;

/**
	*@author :tb
	*@version 2018年4月16日 下午6:06:23
*/
@Service
public class SchoolQuestionService {
	@Autowired
	private SchoolQuestionMapper schoolQuestionMapper=null;
	/**
	 * 通过不同的查询方式查询校园问题的集合
	 * @param questionSelect
	 * @return
	 */
	@Cacheable(value="schoolQuestions",key="'queryType_'+#questionSelect.queryType +'offset_'+#questionSelect.offset+'limit_'+#questionSelect.limit")
	public List<SchoolQuestion> selectList(SchoolQuestionSelect questionSelect){
		//判断是哪种查询
		List<SchoolQuestion> list =null;
		RowBounds rowBounds=new RowBounds(questionSelect.getOffset(), questionSelect.getLimit());
		switch (questionSelect.getQueryType()) {
		case ByQuestionType:
			list=schoolQuestionMapper.selectListByQuestionType(questionSelect.getChoiceType(),rowBounds);
			break;
		case ForNewest:
			list=schoolQuestionMapper.selectListByForNewest(rowBounds);
			break;
		case TopHot:
			list=schoolQuestionMapper.selectListByTopHot(rowBounds);
			break;
		case NoPerfectAnswer:
			list=schoolQuestionMapper.selectListByNoPerfectAnswer(rowBounds);
			break;
		}
		return list;
	}
	
	/**
	 * 某校园问题的详情
	 * @param questionId
	 * @return
	 */
	@Cacheable(value="schoolQuestion",key="'questionId_'+ #questionId")
	public SchoolQuestion selectOneDetail(Integer questionId){
		return schoolQuestionMapper.selectDetail(questionId);
	}
}


