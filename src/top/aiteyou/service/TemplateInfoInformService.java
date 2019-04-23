package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.FormValue;
import top.aiteyou.entity.User;
import top.aiteyou.entity.schoolArticle.Article;
import top.aiteyou.entity.schoolArticle.ArticleComment;
import top.aiteyou.mapper.FormValueMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.mapper.schoolarticle.ArticleCommentMapper;
import top.aiteyou.mapper.schoolarticle.ArticleMapper;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
 *  模板消息通知
	*@author :tb
	*@version 2018年4月18日 下午6:11:16
*/

@Service
public class TemplateInfoInformService {
	//微信第三方api地址
	private  static  final String WeiXinApiTOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private  static  final String WeiXinApiTEMPLATE="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
	//access_token需要的参数
	public static final String AppId1="wx7247cfc9c0b272b8";
	public static final String Secret1="19815bfbec278cf188c7daa8a8ee1b52";
	public static final String AppId2="wx17a8b182dd169e9e";
	public static final String Secret2="eb3ee5173b5d94bedab58af76a28b4bb";
	//模板api需要参数 2小时失效
	private static String ACCESS_TOKEN1="";
	private static Date RegisteTokenTime1=null;
	private static String ACCESS_TOKEN2="";
	private static Date RegisteTokenTime2=null;
	//模板相关
		//提问回复
//		private static final String SchoolQuestionReplyTemplateId="zPEgAuTMNAO-FLfGXjaJI6A2fyFCTaIe-HQ9cvjSwFo";
//		private static final String SchoolQuestionDetailPage="pages/tools/tools-game-today/tools-game-today";
	public static final String FormValueDbName="formvalue";
	public static final String FormValueDbName2="formvalue_app2";
	
	
	@Autowired
	protected UserMapper userMapper=null;
	@Autowired
	private FormValueMapper formValueMapper=null;
	
	/**
	 * @param userId 模板消息的接收人
	 * @param formvalueDbName 推送内容相关的form_id数据表
	 * @param TemplateMessage 已经准备了 TemplateId 、page
	 * 准备不同类型内容的模板消息
	 * @throws Exception 
	 */
	protected String prepareSendTemplateData(Integer acceptUserId,int appId,TemplateMessage templateMessage) throws Exception {
		
		//获取要推送那人的openid
		String openid=userMapper.selectOpenid(acceptUserId);
		if (openid==null) {
			return "缺少openid";//没有用户的openid 不能推送
		}
		//获取要推送那人的formid
		//判断formvalue表
		String formValueTableName=FormValueDbName;
		if (appId==2) {
			formValueTableName=FormValueDbName2;
		}
		FormValue formValue=formValueMapper.selectOneOld(formValueTableName,acceptUserId);
		
		if (formValue==null) {
			return "缺少formid";//缺少条件，取消推送
		}
		//设置模板的发送参数
		templateMessage.setTouser(openid);
		templateMessage.setForm_id(formValue.getFormId());
		String msg=JSON.toJSONString(templateMessage);
		String result= sendTemplateMsg(appId,msg);
		//删除使用过的formValue
		formValue.setDbName(formValueTableName);
		formValueMapper.deleteUse(formValue);
		
		return result;
	}
	
	/*public	String setSchoolQuestionReplyMsgData(User user,String questionTitle,String replyContent ){
		Integer userId=user.getId();
		String userName=user.getName();
		//获取要推送那人的openid
		String openid=userMapper.selectOpenid(userId);
		if (openid==null) {
			return ;
		}
		//获取要推送那人的formid
		FormValue formValue=formValueMapper.selectOneOld(userId);
		if (formValue==null) {
			return ;//缺少条件，取消推送
		}
		
		TemplateMessage templateMessage=new TemplateMessage();
		templateMessage.setTouser(openid);
		templateMessage.setTemplate_id(SchoolQuestionReplyTemplateId);
		templateMessage.setPage(SchoolQuestionDetailPage);
		templateMessage.setForm_id(formValue.getFormId());
		
		Map<String,TemplateMessageKeyWord> map=new LinkedHashMap();
		//截取问题回复内容
		if (replyContent.length()>20) {
			replyContent=replyContent.substring(0, 20);
		}
		map.put("keyword1", new TemplateMessageKeyWord(questionTitle, "#173177"));
		map.put("keyword2", new TemplateMessageKeyWord(userName, "#173177"));
		map.put("keyword3", new TemplateMessageKeyWord(replyContent, "#173177"));
		//设置消息发送时间
		String sendTime=GameController.mouthhourFormat.format(new Date());
		map.put("keyword4", new TemplateMessageKeyWord(sendTime, "#173177"));
		templateMessage.setData(map);
		
		//向微信接口请求
		String msg=JSON.toJSONString(templateMessage);
		String result_str = null;
		result_str = sendTemplateMsg(msg);
		
		//删除刚使用的form-id
		formValueMapper.deleteUse(formValue);
		
		return result_str;
	}*/
	
	private String sendTemplateMsg(int appId,String data)   {
		OutputStreamWriter out=null;
		HttpURLConnection conn =null;
		BufferedReader reader = null;
		String response="";
		try { 
			//判断Access_Token是否过期
			Date now=new Date();
			if (appId==1) {
				if (RegisteTokenTime1==null || (now.getTime()-RegisteTokenTime1.getTime())>5400*1000 ) {
					getAccessToken1();
				}
			}else if (appId==2) {
				if (RegisteTokenTime2==null || (now.getTime()-RegisteTokenTime2.getTime())>5400*1000 ) {
					getAccessToken2();
				}
			}
			String access_token_temp=ACCESS_TOKEN1;
			if (appId==2) {
				access_token_temp=ACCESS_TOKEN2;
			}
			String url_str=WeiXinApiTEMPLATE+""+access_token_temp;
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
				Logger.getLogger(getClass()).info("发送模板消息失败"+e1.toString());
			}
		}
		Logger.getLogger(getClass()).info("发送模板消息提示"+response);
		return response;
        
}
	private  void getAccessToken1() throws Exception{
		String url_str=WeiXinApiTOKEN+"&appid="+AppId1+"&secret="+Secret1;
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
			ACCESS_TOKEN1= map.get("access_token");
	    	RegisteTokenTime1=new Date();
	    }
    
	}
	private  void getAccessToken2() throws Exception{
		String url_str=WeiXinApiTOKEN+"&appid="+AppId2+"&secret="+Secret2;
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
			ACCESS_TOKEN2= map.get("access_token");
	    	RegisteTokenTime2=new Date();
	    }
    
	}
	
	
}


