package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.aiteyou.entity.UserReallyInfo;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.UserReallyInfoMapper;
import top.aiteyou.transform.ClassGrade;

/**
	*@author :tb
	*@version 2018年5月26日 下午12:10:58
*/
@Service
public class UserReallyInfoService{
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private UserReallyInfoMapper userReallyInfoMapper=null;
	/**
	 * 课程中心拉取 用户信息
	 * @param userReallyInfo
	 */
	public void registerStudentGetReallyInfo(String studentNumber) {
		
	}
	/**
	 * 在第三方平台注册
	 * userId +studentNumber+studentPassword
	 * @throws URLException 
	 */
	
	public void registerStudentByHkHand(Integer userReallyInfoId,Integer userId,String studentNumber,String studentPassword)  {
		
		String openId=userMapper.selectOpenid(userId);
		//openId 掺沙
		String userId_str=userId.toString();
		String openId_temp=openId.substring(0, openId.length()-userId_str.length())+userId_str;
		
		String url_str="http://service.hkhand.com/pro/bind";
		
		String param="{\"openid\":\""+openId_temp+"\",\"stu_number\":"+studentNumber+",\"stu_password\":"+studentPassword+"}";
		
		HkHandObject hkHandObject=new HkHandObject();
		hkHandObject.setOpenid(openId);
		hkHandObject.setStu_number(studentNumber);
		hkHandObject.setStu_password(studentPassword);
		param=JSONObject.toJSONString(hkHandObject);
		Map<String , Object> map =null;
		try {
			
		
		URL url=new URL(url_str);
        //1.获得连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //2.设置
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);//设置不要缓存
        connection.setInstanceFollowRedirects(true);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(param);
        out.flush();
        StringBuffer stringBuffer =new StringBuffer();
        if (connection.getResponseCode() ==200) {
        	InputStreamReader inputStream = new InputStreamReader (connection.getInputStream());
        	String str=null;
            BufferedReader reader=new BufferedReader(inputStream);
            while( (str=reader.readLine()) !=null){
            	stringBuffer.append(str);
            }
            //释放io，断开连接
            inputStream.close();
        }
        connection.disconnect();

        
        //转换json数据
        if (! stringBuffer .toString() .equals("")) {
			 map=(Map<String, Object>) JSON.parse(stringBuffer.toString());
        }
        } catch (Exception e) {
			Logger.getLogger(getClass()).error("hkhand 注册失败"+e.getMessage());
		}
		if (map!=null) {
			boolean status=(boolean) map.get("status");
			if (status) {
				userReallyInfoMapper.updateBindSuccess(userReallyInfoId,1);
			}else{
				userReallyInfoMapper.updateBindSuccess(userReallyInfoId,2);
			}
		}
		
	}
	
	
	
	/**
	 * 请求第三方获取本学期的课程表
	 * @param userId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getClasses(Integer userId) throws UnsupportedEncodingException {
		//如果之前没有注册成功
		UserReallyInfo userReallyInfo=userReallyInfoMapper.selectByUserId(userId);
		Integer isBand=userReallyInfo.getIsBand();
		//重新注册
		if (isBand!=1) {
			registerStudentByHkHand(userReallyInfo.getId(), userId, userReallyInfo.getStudentNumber(), userReallyInfo.getStudentPassword());
		}
		
		
		String openId=userMapper.selectOpenid(userId);
		//openId 掺沙
		String userId_str=userId.toString();
		String openId_temp=openId.substring(0, openId.length()-userId_str.length())+userId_str;
				
		if (openId==null) {
			return "没有你的openid";
		}
		String url_str="http://service.hkhand.com/pro/course?openid="+openId;
		StringBuffer stringBuffer=new StringBuffer();
		Map<String , String> map =null;
		try {
	
		URL url=new URL(url_str);
        //1.获得连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //2.设置
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(1000);
        connection.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
        
        connection.connect();
         stringBuffer =new StringBuffer();
        if (connection.getResponseCode() ==200) {
        	InputStreamReader inputStream = new InputStreamReader (connection.getInputStream());
        	String str=null;
            BufferedReader reader=new BufferedReader(inputStream);
            while( (str=reader.readLine()) !=null){
            	stringBuffer.append(str);
            }
            //释放io，断开连接
            inputStream.close();
        }
        connection.disconnect();
		
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("hkhand 获取课程表失败");
		}
        
		return stringBuffer.toString();

}
	public List<ClassGrade> getClassGrade(Integer userId) {
		//获取学号
		
		UserReallyInfo userReallyInfo= userReallyInfoMapper.selectByUserId(userId);
		String studentNumber=userReallyInfo.getStudentNumber();
		String studentPassword=userReallyInfo.getStudentPassword();
		if (studentNumber==null) {
			return null;
		}
		UserReallyUtilsGetClassGrade userReallyUtilsGetClassGrade=new UserReallyUtilsGetClassGrade();
		String cookie=userReallyUtilsGetClassGrade.getcookies();
		userReallyUtilsGetClassGrade.getSession(cookie, studentNumber, studentPassword);
		return userReallyUtilsGetClassGrade.getClassGrade(cookie);
	}
	
	
}

class HkHandObject{
	private String openid;
	
	private String stu_number;
	
	private String stu_password;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getStu_number() {
		return stu_number;
	}

	public void setStu_number(String stu_number) {
		this.stu_number = stu_number;
	}

	public String getStu_password() {
		return stu_password;
	}

	public void setStu_password(String stu_password) {
		this.stu_password = stu_password;
	}
	
}
