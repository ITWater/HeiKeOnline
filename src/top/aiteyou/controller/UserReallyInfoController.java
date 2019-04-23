package top.aiteyou.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.UserReallyInfo;
import top.aiteyou.mapper.UserReallyInfoMapper;
import top.aiteyou.service.UserReallyInfoService;
import top.aiteyou.service.UserReallyUtilsGetUserInfo;
import top.aiteyou.transform.ClassGrade;

/**
	*@author :tb
	*@version 2018年5月26日 下午12:04:05
*/
@Controller
@RequestMapping(value="/reallyuserinfo")
public class UserReallyInfoController {
	@Autowired
	private UserReallyInfoService userReallyInfoService=null;
	@Autowired
	private UserReallyInfoMapper userReallyInfoMapper=null;
	/**
	 * 课程中心抓取真实信息 ，保存，Hkhand注册
	 * @param userReallyInfo
	 * @return
	 */
	@RequestMapping(value="/register/{userId}/{studentNumber}/{password}",method=RequestMethod.GET)
	@ResponseBody
	public UserReallyInfo register2(@PathVariable("studentNumber")String studentNumber,@PathVariable("userId")Integer userId,@PathVariable("password")String password){
		
		//获取真实信息 同步
		UserReallyUtilsGetUserInfo userReallyUtils=new UserReallyUtilsGetUserInfo();
		String cookie=userReallyUtils.getcookies();
		userReallyUtils.getSession(cookie, studentNumber, studentNumber);//课程中心 password=studentNumber
		UserReallyInfo userReallyInfo=userReallyUtils.getUserInfo(cookie);
		if (userReallyInfo==null) {
			return null;
		}
		userReallyInfo.setUserId(userId);
		userReallyInfo.setStudentPassword(password);
		//保存到db
		userReallyInfoMapper.insert(userReallyInfo);
		
		//hkhand注册  异步
//		String studentPassword=userReallyInfo.getStudentPassword(); 课程中心的密码
		Integer userReallyInfoId=userReallyInfo.getId();
		
		String studentPassword="1";//教务网的默认密码
		if (password!=null && !password.equals("")) {
			studentPassword=password;
		}
		userReallyInfoService.registerStudentByHkHand(userReallyInfoId,userId,studentNumber,studentPassword);
		
		//返回带有真实信息的对象
		return  userReallyInfo;
	}
	/**
	 * 课程中心抓取真实信息 ，保存，Hkhand注册
	 * @param userReallyInfo
	 * @return
	 */
	@RequestMapping(value="/register/{userId}/{studentNumber}",method=RequestMethod.GET)
	@ResponseBody
	public UserReallyInfo register(@PathVariable("studentNumber")String studentNumber,@PathVariable("userId")Integer userId){
		
		//获取真实信息 同步
		UserReallyUtilsGetUserInfo userReallyUtils=new UserReallyUtilsGetUserInfo();
		String cookie=userReallyUtils.getcookies();
		userReallyUtils.getSession(cookie, studentNumber, studentNumber);//课程中心 password=studentNumber
		UserReallyInfo userReallyInfo=userReallyUtils.getUserInfo(cookie);
		if (userReallyInfo==null) {
			return null;
		}
		userReallyInfo.setUserId(userId);
		//保存到db
		userReallyInfoMapper.insert(userReallyInfo);
		
		//hkhand注册  异步
//		String studentPassword=userReallyInfo.getStudentPassword(); 课程中心的密码
		Integer userReallyInfoId=userReallyInfo.getId();
		
		String studentPassword="1";//教务网的默认密码
		
		userReallyInfoService.registerStudentByHkHand(userReallyInfoId,userId,studentNumber,studentPassword);
		
		//返回带有真实信息的对象
		return  userReallyInfo;
	}
	/**
	 * 获取本学期成绩 
	 * @return
	 */
	@RequestMapping(value="/classgrade/{id}")
	@ResponseBody
	public  List<ClassGrade> getClassGrade(@PathVariable("id")Integer userId){
			return userReallyInfoService.getClassGrade(userId);
	}
	/**
	 * 获取课表 注册之后
	 * @return
	 */
	@RequestMapping(value="/classes/{id}")
	@ResponseBody
	public String getClasses(@PathVariable("id")Integer userId){
		try {
			return userReallyInfoService.getClasses(userId);
		} catch (UnsupportedEncodingException e) {
			return "error";
		}
	}
	
}



