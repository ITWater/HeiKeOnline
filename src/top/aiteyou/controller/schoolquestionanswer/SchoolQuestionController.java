package top.aiteyou.controller.schoolquestionanswer;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.schoolquestionanswer.SchoolQuestion;
import top.aiteyou.mapper.schoolquestionanswer.SchoolQuestionMapper;
import top.aiteyou.service.schoolquestionanswer.SchoolQuestionService;
import top.aiteyou.transform.schoolquestionanswer.SchoolQuestionSelect;

/**
	*@author :tb
	*@version 2018年4月16日 下午3:00:24
*/
@Controller
@RequestMapping(value="/schoolquestion")
public class SchoolQuestionController {

	@Autowired
	private SchoolQuestionService schoolQuestionService=null;
	/**
	 * 查询某校园提问的详情
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public SchoolQuestion selectOneDetail(@PathVariable("id") Integer questionId){
		return schoolQuestionService.selectOneDetail(questionId);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public List<SchoolQuestion> selectList(@RequestBody SchoolQuestionSelect schoolQuestionSelect){
		return schoolQuestionService.selectList(schoolQuestionSelect);
		
	}
	
}


