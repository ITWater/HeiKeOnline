package top.aiteyou.controller.schoolarticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.schoolArticle.UserConcernInfo;
import top.aiteyou.service.schoolarticle.UserConcernService;

/**
	*@author :tb
	*@version 2018年4月21日 上午9:49:19
*/
@Controller
@RequestMapping(value="/schoolarticle/user")
public class SchoolArticleUserController {
	@Autowired
	private UserConcernService userConcernService=null;
	
	@RequestMapping(value="/concern",method=RequestMethod.POST)
	@ResponseBody
	public int concernUser(@RequestBody UserConcernInfo userConcernInfo){
		userConcernService.concernUser(userConcernInfo);
		return 1;
	}
	
	@RequestMapping(value="/cancel",method=RequestMethod.POST)
	@ResponseBody
	public int cancelConcernUser(@RequestBody UserConcernInfo userConcernInfo){
		 userConcernService.cancelConcernUser(userConcernInfo);
		 return 1;
	}

}


