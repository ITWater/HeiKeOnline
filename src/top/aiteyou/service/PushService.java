package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.FormValue;
import top.aiteyou.entity.Game;
import top.aiteyou.entity.User;
import top.aiteyou.entity.UserConcern;
import top.aiteyou.mapper.FormValueMapper;
import top.aiteyou.mapper.GameMapper;
import top.aiteyou.mapper.UserConcernMapper;
import top.aiteyou.mapper.UserMapper;
import top.aiteyou.transform.GameOverInfo;
import top.aiteyou.transform.TemplateMessage;
import top.aiteyou.transform.TemplateMessageKeyWord;

/**
	*@author :tb
	*@version 2018年4月1日 下午9:41:30
*/
@Service
public class PushService {
	private  static  final String WeiXinApiTOKEN="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private  static  final String WeiXinApiTEMPLATE="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
	public static final String AppId1="wx7247cfc9c0b272b8";
	public static final String Secret1="19815bfbec278cf188c7daa8a8ee1b52";
	public static final String AppId2="wx17a8b182dd169e9e";
	public static final String Secret2="eb3ee5173b5d94bedab58af76a28b4bb";
	
	private static String ACCESS_TOKEN1="";
	private static Date RegisteTokenTime1=null;
	private static String ACCESS_TOKEN2="";
	private static Date RegisteTokenTime2=null;
	
	@Autowired
	private UserMapper userMapper=null;
	@Autowired
	private FormValueMapper formValueMapper=null;
	@Autowired
	private UserConcernMapper userConcernMapper=null;
	@Autowired
	private GameMapper gameMapper=null;
	//模板相关
	public static final String FormValueDbName="formvalue";
	public static final String FormValueDbName2="formvalue_app2";
		//赛事预告模板
		public static final String game_template_id1="bzPzizwmgsno0Wgl4nNSSK0NCLI4M7pPsgQ34zmpSD0";
		public static final String game_template_id2="mIar6aj7XSOv5qjdIZpwCLF0XhwhH2a4iKDUX_bgnLE";
		//赛事结果模板
		public static final String gameover_template_id1="RZsqv2qtCNCBe9pYS-3p4GZVykpOp5UfxxC9i6HvZUc";
		public static final String gameover_template_id2="o6hOkGFMcJIQmS0c7Kw0iAft62k5ekRqWqbRvh3ZvM0";
	private Calendar calendar=Calendar.getInstance();

	public List<String> GamePushServer(int appId,int gameId) throws Exception {
		List<String> data=new ArrayList<String>();
		
		//如果没有则获取token_id
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
		
			Date today=new Date();//今天
			
			//获取明日赛事的赛程
			calendar.setTime(today);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			String tomorrow_str=GameController.dateFormat.format(calendar.getTime());
			calendar.setTime(today);
			calendar.add(Calendar.DAY_OF_YEAR, 2);
			String thedayaftertomorrow=GameController.dateFormat.format(calendar.getTime());
			
			
			List<Game> games=gameMapper.selectTorrowGameJoinTeam(tomorrow_str,thedayaftertomorrow,gameId);
			if (games.size()==0) {
				return null;//如果明天没有赛事
			}
			//*******************选择不同提示
			String gameKind="";
			switch (gameId) {
			case 0:
				gameKind="足球联赛";
				break;
			case 1:
				gameKind="足球杯赛";
				break;
			case 2:
				gameKind="火神杯男篮";
				break;
			case 3:
				gameKind="火神杯女篮";
				break;
			}
			String gamesize="明日共有"+games.size()+"场"+ gameKind;
			//.准备推送内容
			StringBuffer content=new StringBuffer();
			for (Game game : games) {
				//时间友好处理
				String time_str=GameController.hourFormat.format(game.getTime());
				content.append(time_str+" "+game.getTeam1().getName()+" VS " +game.getTeam2().getName());
				content.append("\r\n");
			}
			//1.获取关注人的userid
			List<UserConcern> concernusers=userConcernMapper.selectUserConcernByGameId(gameId,gameId);//查询所有关注了杯赛、联赛的用户信息
				//每个人关注遍历
			for (UserConcern userConcern : concernusers) {
				int userId=userConcern.getUserId();
				String openid=userMapper.selectOpenid(userId);
				
				//判断formvalue表
				String formValueTableName=FormValueDbName;
				if (appId==2) {
					formValueTableName=FormValueDbName2;
				}
				//2.获取每人的一个form id
				FormValue formValue=formValueMapper.selectOneOld(formValueTableName,userId);
				if (formValue ==null) {
					continue;//如果这人没有formid 不推送，跳过
				}
				//4.发起请求
				TemplateMessage templateMessage=new TemplateMessage();
				templateMessage.setTouser(openid);
				//选择模板id
				String game_template_id_temp=game_template_id1;
				if (appId==2) {
					game_template_id_temp=game_template_id2;
				}
				templateMessage.setTemplate_id(game_template_id_temp);
				
				//**************选择不同赛事跳转页面
				templateMessage.setPage("pages/tools/tools-game-today/tools-game-today?matchId="+gameId);
				
				
				templateMessage.setForm_id(formValue.getFormId());
				Map<String,TemplateMessageKeyWord> map=new LinkedHashMap();
				map.put("keyword1", new TemplateMessageKeyWord(tomorrow_str, "#173177"));
				map.put("keyword2", new TemplateMessageKeyWord(gamesize, "#173177"));
				map.put("keyword3", new TemplateMessageKeyWord(content.toString(), "#173177"));
				templateMessage.setData(map);
				
				//数据格式化
				String msg=JSON.toJSONString(templateMessage);
				String result_str=sendTemplateMsg(appId,msg);
				data.add(result_str);
				
				
				//5. 删除form id
				formValue.setDbName(formValueTableName);
				formValueMapper.deleteUse(formValue);
			}
		return data;
	}
	@Async//应该是无效的
	public String sendTemplateMsg(int appId,String data) {
			OutputStreamWriter out=null;
			HttpURLConnection conn =null;
			BufferedReader reader = null;
			String response="";
			try {
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
					Logger.getLogger(getClass()).info("发送足球赛事推送出错appId"+appId+e1.toString());
				}
			}
			return response;
	        
	}
	public  void getAccessToken1() throws Exception{
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
	public  void getAccessToken2() throws Exception{
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
	public List<String> GameOverPushServer(int appId,GameOverInfo gameOverInfo) {	
		List<String> data=new ArrayList<String>();
		
		//如果没有则获取token_id
		Date now=new Date();
		try {
			if (appId==1) {
				if (RegisteTokenTime1==null || (now.getTime()-RegisteTokenTime1.getTime())>5400*1000 ) {
					getAccessToken1();
				}
			}else if (appId==2) {
				if (RegisteTokenTime2==null || (now.getTime()-RegisteTokenTime2.getTime())>5400*1000 ) {
					
						getAccessToken2();
					
				}
			}
		} 
		catch (Exception e) {
			Logger.getLogger(getClass()).error("获取acess_tokent失败");
		}
			
		
		//获取本次推送赛事的数据
		Game game=gameMapper.selectOneJoinTeam(gameOverInfo.getGameId());
			
			if (game==null) {
				data.add("找不到比赛");//找不到比赛
				return data;
			}
			
			//.准备推送内容
			String gameDuiZhen=game.getTeam1().getName()+" VS "+ game.getTeam2().getName();
			String gameTime=GameController.mouthhourFormat.format(game.getTime());
			String gameAdress=game.getLocation();
			StringBuffer content=new StringBuffer();
			String gameResult=game.getScore1()+" : "+game.getScore2();
			String gameInfo=gameOverInfo.getInfo();
			
			
			//1.获取关注人的userid
			List<Integer> infoRange=Arrays.asList(gameOverInfo.getInfoRange());
			List<UserConcern> concernusers=userConcernMapper.selectUserConcernByRange(infoRange);
			
			for (UserConcern userConcern : concernusers) {
				int userId=userConcern.getUserId();
				String openid=userMapper.selectOpenid(userId);
				
				//2.获取每人的一个form id
				//判断formvalue表
				String formValueTableName=FormValueDbName;
				if (appId==2) {
					formValueTableName=FormValueDbName2;
				}
				//2.获取每人的一个form id
				FormValue formValue=formValueMapper.selectOneOld(formValueTableName,userId);
				if (formValue ==null) {
					continue;//如果这人没有formid 不推送，跳过
				}
				//4.发起请求
				TemplateMessage templateMessage=new TemplateMessage();
				templateMessage.setTouser(openid);
				//选择模板id
				String template_id_temp=gameover_template_id1;
				if (appId==2) {
					template_id_temp=gameover_template_id2;
				}
				templateMessage.setTemplate_id(template_id_temp);
				
				//**************选择不同赛事跳转页面  //跳转页gameId 和 关注的gameId 是一个含义！
				templateMessage.setPage("pages/tools/tools-game-today/tools-game-today?matchId="+game.getGameId());
				
				
				templateMessage.setForm_id(formValue.getFormId());
				Map<String,TemplateMessageKeyWord> map=new LinkedHashMap();
				map.put("keyword1", new TemplateMessageKeyWord(gameDuiZhen, "#173177"));
				map.put("keyword2", new TemplateMessageKeyWord(gameTime, "#173177"));
				map.put("keyword3", new TemplateMessageKeyWord(gameAdress, "#173177"));
				map.put("keyword4", new TemplateMessageKeyWord(gameResult, "#173177"));
				map.put("keyword5", new TemplateMessageKeyWord(gameInfo, "#173177"));
				templateMessage.setData(map);
				
				//数据格式化
				String msg=JSON.toJSONString(templateMessage);
				String result_str=sendTemplateMsg(appId,msg);
				data.add(result_str);
				
				//5. 删除form id
				formValue.setDbName(FormValueDbName);
				formValueMapper.deleteUse(formValue);
				
			}
		return data;
	}

}


