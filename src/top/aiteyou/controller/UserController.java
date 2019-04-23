package top.aiteyou.controller;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import oracle.net.aso.u;
import top.aiteyou.entity.SystemMsg;
import top.aiteyou.entity.User;
import top.aiteyou.entity.UserConcern;
import top.aiteyou.mapper.UserConcernMapper;
import top.aiteyou.service.UserService;
import top.aiteyou.websocket.SystemMsgWebSocketService;

/**
	*@author :tb
	*@version 2018年4月1日 上午10:51:07
*/
@Controller
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	private UserService userService=null;
	@Autowired
	private UserConcernMapper userConcernMapper=null;
	@Autowired
	private SystemMsgWebSocketService systemMsgWebsocket=null;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public User getUserBaseInfo(@PathVariable("id")Integer userId){
		return userService.getUserBaseData(userId);
	}
	
	/**
	 * 用户关注
	 * @param userConcern
	 * @return
	 */
	@RequestMapping(value="/concern",method=RequestMethod.POST)
	@ResponseBody
	public int userConcern(@RequestBody UserConcern userConcern){
		return userConcernMapper.insert(userConcern);
	}
	/**
	 * 用户取消关注
	 * @param userConcern
	 * @return
	 */
	@RequestMapping(value="/concern",method=RequestMethod.DELETE)
	@ResponseBody
	public int userCancelConcern(@RequestBody UserConcern userConcern){
		return userConcernMapper.delete(userConcern);
	}
	
	/**
	 * 用户注册
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/app{appId}/register",method=RequestMethod.GET)
	@ResponseBody
	public User wexinLogin(@PathVariable("appId")int appId,String code) throws Exception{
		User user=	userService.registerUser(appId,code);
		user.setSessionkey(null);
		user.setOpenid(null);
		//异步 发送欢迎来到黑科Online
		SystemMsg systemMsg=new SystemMsg(user.getId(), "恭喜你成为黑科Online的首批用户，在今后的相伴的日子里，希望能够得到您的信任和支持。加油吧 黑科大学子^_^", new Timestamp(System.currentTimeMillis()));
		systemMsgWebsocket.saveAndSendSystemMsg(systemMsg);
		return user;
	}
	/**
	 * 更新用户在数据库中的基本信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public int updateUserInfo(@RequestBody User user){
		return userService.updateSelective(user);
	}
	/**
	 * 更新用户sessionkey
	 * @param id
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update",method=RequestMethod.GET)
	@ResponseBody
	public User weixinUpdateSession(@Param("appId")int appId,@Param(value="id")Integer id,@Param(value="code")String code) throws Exception{
		User user=userService.updateSessionKey(appId,id,code);
		user.setSessionkey(null);
		user.setOpenid(null);
		return user;
	}
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR,reason="用户登录请求session_key错误")
	public String HandleParseException(ParseException exception){
		Logger.getLogger(getClass()).error("用户登录请求session_key错误"+exception.toString());
		return "error";
	}
	
	
}


