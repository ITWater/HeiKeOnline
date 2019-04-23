package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import top.aiteyou.entity.User;
import top.aiteyou.mapper.UserMapper;

/**
	*@author :tb
	*@version 2018年4月1日 上午11:05:59
*/
@Service
public class UserServiceImpl implements UserService{
	public static final String WeiXinApi="https://api.weixin.qq.com/sns/jscode2session";
	public static final String AppId1="wx7247cfc9c0b272b8";
	public static final String Secret1="19815bfbec278cf188c7daa8a8ee1b52";
	public static final String AppId2="wx17a8b182dd169e9e";
	public static final String Secret2="eb3ee5173b5d94bedab58af76a28b4bb";
	
	@Autowired
	private UserMapper userMapper=null;
	
	@Override
	public  User registerUser(int appId,String code)throws Exception{
		User user=getUserOpenIdSessionKey(appId,code);
		user.setAppId(appId);
		Timestamp now=new Timestamp(System.currentTimeMillis());
		user.setRegisterTime(now);
		userMapper.insert(user);
		return user;
	}
	
	@Override
	public User getUserOpenIdSessionKey(int appId,String code) throws Exception  {
		String url_str=WeiXinApi+"?"+"appid="+AppId1+"&secret="+Secret1+"&js_code="+code+"&grant_type="+"authorization_code";
		if (appId==2) {
			url_str=WeiXinApi+"?"+"appid="+AppId2+"&secret="+Secret2+"&js_code="+code+"&grant_type="+"authorization_code";
		}
		URL url=new URL(url_str);
        //1.获得连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //2.设置
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(1000);
        connection.connect();
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

        User user=new User();
        
        //转换json数据
        if (! stringBuffer .toString() .equals("")) {
			Map<String , String> map=(Map<String,String>)JSON.parse(stringBuffer.toString());
        	user.setSessionkey(map.get("session_key"));
        	user.setOpenid((map.get("openid")));
        }
        return user;
        
	}

	@CacheEvict(value="user_baseData",key="'userId' +#user.id")
	@Override
	public int updateSelective(User user) {
		return userMapper.updateBaseData(user);
	}

	@Override
	public User updateSessionKey(int appId,Integer id, String code) throws Exception {
		User user=getUserOpenIdSessionKey(appId,code);
		user.setId(id);
		userMapper.updateSessionKey(user);
		return user;
	}
	@Cacheable(value="user_baseData",key="'userId' +#userId")
	@Override
	public User getUserBaseData(Integer userId) {
		return userMapper.selectUserBaseData(userId);
	}

	@Cacheable(value="alladmin_user",key="'allusers'")
	@Override
	public List<User> getAllAdminUser() {
		return userMapper.selectAllAdminUser();
	}

}


