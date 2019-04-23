package top.aiteyou.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import top.aiteyou.entity.CustomerMessage;
import top.aiteyou.mapper.CustomerMessageMapper;
import top.aiteyou.transform.CustomerMsg;
import top.aiteyou.transform.CustomerMsgFrom;
import top.aiteyou.transform.CustomerMsgImage;

/**
 * 客户消息
	*@author :tb
	*@version 2018年5月31日 下午7:01:24
*/
@Controller
@RequestMapping(value="/customerMsg")
public class CustomerMsgController {
	@Autowired
	private CustomerMessageMapper customerMessageMapper=null;
	
	
	private  static  final String WeiXinApiTOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private  static  final String WeiXinApiTEMPLATE="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
	public static final String AppId="wx17a8b182dd169e9e";
	public static final String Secret="eb3ee5173b5d94bedab58af76a28b4bb";
	private static String SendCustomerMsgAPI="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	//只能是app2 HeiKeOnline
	private static String ACCESS_TOKEN="";
	private static Date RegisteTokenTime=null;
	
	private static final String ImageUrl="https://heikeonline.oss-cn-beijing.aliyuncs.com/customerMsg/yikatong.jpg";
	private static final String AnXinFuUrl="https://h5.axinfu.com/";
	
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String replyCustomerMsg(@RequestBody CustomerMsgFrom customerMsgFrom){ 
		CustomerMessage customerMessage=new CustomerMessage(null, customerMsgFrom.getFromUserName(), customerMsgFrom.getContent(), new Timestamp(System.currentTimeMillis()));
		customerMessageMapper.insert(customerMessage);
		//只发送充值饭卡的
		CustomerMsgImage image=new CustomerMsgImage("黑科大饭卡充值", "", AnXinFuUrl, ImageUrl);
		
		CustomerMsg customerMsg=new CustomerMsg(customerMsgFrom.getFromUserName(), "link", image);
		//异步发送
		sendCustomerMsg(customerMsg);
		return "success";
		
	}
	@Async
	private void sendCustomerMsg(CustomerMsg customerMsg){
		String data=JSONObject.toJSONString(customerMsg);
		
		OutputStreamWriter out=null;
		HttpURLConnection conn =null;
		BufferedReader reader = null;
		String response="";
		try { 
			//判断Access_Token是否过期
			Date now=new Date();
			if (RegisteTokenTime==null || (now.getTime()-RegisteTokenTime.getTime())>3600*1000 ) {
				try {
					getAccessToken();
				} catch (Exception e) {
					
				}
			}
		
			String url_str=SendCustomerMsgAPI+""+ACCESS_TOKEN;
			URL url=new URL(url_str);
	        //1.获得连接
			conn= (HttpURLConnection) url.openConnection();
	        //2.设置
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setUseCaches(false);//设置不要缓存
	        conn.setInstanceFollowRedirects(true);
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.connect();
	        out = new OutputStreamWriter(conn.getOutputStream());
			out.write(data);
	        out.flush();
	        reader = new BufferedReader(new InputStreamReader(
	        conn.getInputStream()));
	        String lines;
	        while ((lines = reader.readLine()) != null){
	        	lines = new String(lines.getBytes(), "utf-8");
	        		response+=lines;
	        } 
		}
        catch (Exception e) {
			try {
				out.close();
				conn.disconnect();
			} catch (IOException e1) {
				Logger.getLogger(getClass()).info("发送客户消息失败"+e1.toString());
			}
		}
		Logger.getLogger(getClass()).info("发送客户消息提示"+response);
	}
	
	private  void getAccessToken() throws Exception{
		String url_str=WeiXinApiTOKEN+"&appid="+AppId+"&secret="+Secret;
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
	
	    
	    //转换json数据
	    if (! stringBuffer .toString() .equals("")) {
			Map<String , String> map=(Map<String,String>)JSON.parse(stringBuffer.toString());
	    	//赋值
			ACCESS_TOKEN= map.get("access_token");
	    	RegisteTokenTime=new Date();
	    }
    
	}
}




