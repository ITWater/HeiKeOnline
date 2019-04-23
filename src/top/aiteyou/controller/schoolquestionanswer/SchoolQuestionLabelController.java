package top.aiteyou.controller.schoolquestionanswer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.schoolquestionanswer.SchoolQuestionLabel;
import top.aiteyou.mapper.schoolquestionanswer.SchoolQuestionLabelMapper;

/**
	*@author :tb
	*@version 2018年4月16日 下午9:14:09
*/
@Controller
@RequestMapping(value="/schoolquestionlabels")
public class SchoolQuestionLabelController {
	@Autowired
	private SchoolQuestionLabelMapper labelMapper=null;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public List<SchoolQuestionLabel> selectAllLable(){
		return labelMapper.selectAll();
	}
	
}


