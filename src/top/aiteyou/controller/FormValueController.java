package top.aiteyou.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.FormValue;
import top.aiteyou.service.FormValueService;

/**
	*@author :tb
	*@version 2018年4月2日 上午10:09:33
*/
@Controller
@RequestMapping(value="/formvalues")
public class FormValueController {
	
	@Autowired
	private FormValueService formValueService=null;
	/**
	 * 新增一个formid
	 * 指定属性 appId
	 * @param formValue
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public int addFormValue(@RequestBody FormValue formValue){
		formValue.setTime(new Timestamp(System.currentTimeMillis()));
		return formValueService.insertOne(formValue);
	}
	

}


