package top.aiteyou.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.aiteyou.entity.InviteCode;
import top.aiteyou.entity.User;
import top.aiteyou.mapper.InviteCodeMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.InviteCodeAuthor;
import top.aiteyou.transform.UserIdentityUp;
import top.aiteyou.transform.UserMsg;

/**
	*@author :tb
	*@version 2018年4月26日 上午11:40:46
*/
@Controller
@RequestMapping(value="/invitecodes")
public class InviteCodeController {
	@Autowired
	private InviteCodeMapper inviteCodeMapper=null;
	@Autowired
	private UserMapper userMapper=null;
	
	/**
	 * 认证身份升级
	 */
	@RequestMapping(value="/author",method=RequestMethod.POST)
	@ResponseBody
	public UserMsg authorUpIdentity(@RequestBody UserIdentityUp up){
		String msg="";
		User user=null;
		
		Integer userId=up.getUserId();
		String code=up.getCode();
		user=userMapper.seltctVipLevel(userId);
		Integer kindof=user.getVipLevel();
		
//		if (kindof==null || kindof<0) {
//			msg="升级指标出错";
//			return new UserMsg(0,msg,user);
//		}
		//1.判断用户合法性
		if (user==null) {
			msg="用户信息找不到,请反馈工作人员";
			return new UserMsg(0,msg,user);
		}
		InviteCode inviteCode=inviteCodeMapper.selectByCode(code);
		//2.判断邀请码的合法性
		if (inviteCode==null) {
			msg="邀请码无效 ):";
			return new UserMsg(0,msg,user);
		}
//		if (inviteCode.getKindof()!= kindof ) {
//			msg="此邀请码种类与升级用户级别不匹配";
//			return new UserMsg(0,msg,user);
//		}
		if (inviteCode.getUserId()!=null) {
			msg="此邀请码已经被使用过了 ):";
			return new UserMsg(0,msg,user);
		}
		
		//合法用户 合法邀请码 升级用户 更新邀请码
		user.setVipLevel(inviteCode.getKindof());
		int result1=userMapper.updateIdentity(user);
		
		inviteCode.setUserId(userId);
		inviteCodeMapper.useCode(inviteCode);
		msg="认证成功 (:";
		UserMsg userMsg=new UserMsg(0,msg,user);
		if (result1==1) {
			userMsg.setResult(1);
		}
		return userMsg;
	}
	
	
	
	/**
	 * 生成邀请码
	 * @param author
	 * @return
	 */
	@RequestMapping(value="/generate",method=RequestMethod.POST)
	@ResponseBody
	public List<String> generateCode(@RequestBody InviteCodeAuthor author){
		List<String> result=new ArrayList<String>();
		User user=userMapper.selectUserIdentity(author.getUserId());
		String password=author.getPassword();
		if (user==null) {
			result.add("找不到用户");
			return result;
		}
		if (!user.getPassword() .equals(password)) {
			result.add("用户密码错误");
			return result;
		}
		if(user.getVipLevel() !=1){
			result.add("用户级别不够");
			return result;
		}
		int count=author.getGenerateCount();
		if (count<=0) {
			result.add("生成数量应>0");
			return result;
		}
		List<InviteCode> data=new ArrayList<>();
		Integer kindof=author.getKindof();
		
		//开始生成
		for(int i=0;i<count;i++){
			//准备每一个邀请码的参数
			String code=UUID.randomUUID().toString().toUpperCase().replace("-", "");
			Timestamp time=new Timestamp(System.currentTimeMillis());
			InviteCode inviteCode=new InviteCode(code,time , kindof);
			data.add(inviteCode);
		}
		inviteCodeMapper.insertBatch(data);
		for (InviteCode inviteCode : data) {
			result.add(inviteCode.getCode());
		}
		return result;
	}
}


