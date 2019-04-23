package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;

import top.aiteyou.entity.UserReallyInfo;


/**
	*@author :tb
	*@version 2018年5月26日 下午12:40:54
*/

public class UserReallyUtilsGetUserInfo {
	
	private String cookie;
	public static String A_URL="http://lc.usth.net.cn/G2S/ShowSystem/Login.ashx";
	
//	public static void main(String[] args) {
//		int i=3;
//		int j=3;
//		int k=3;
//		for( i=0;i<10;i++){
//			for( j=0;j<10;j++){
//				for( k=0;k<10;k++){
//			UserReallyUtils userReallyUtils=new UserReallyUtils();
//			String cookie=userReallyUtils.getcookies();
//			String studentNumber="2016024"+k+i+j;
//			userReallyUtils.getSession(cookie, studentNumber, studentNumber);
//			UserReallyInfo userReallyInfo=userReallyUtils.getUserInfo(cookie);
//			System.out.println(userReallyInfo);
//		}}}
//	}
	
	 /** 
     * 对字符串md5加密(小写+字母) 
     * 
     * @param str 传入要加密的字符串 
     * @return  MD5加密后的字符串 
     */  
    public static String getMD5(String str) {  
        try {  
            // 生成一个MD5加密计算摘要  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            // 计算md5函数  
            md.update(str.getBytes());  
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
            return new BigInteger(1, md.digest()).toString(16);  
        } catch (Exception e) {  
           e.printStackTrace();  
           return null;  
        }  
    }  
	public static void getSession(String cookie,String studentNumber,String studentPassword){
		
	//密码加密
	studentPassword=getMD5(studentPassword);
		
	String url_str=A_URL+"?LoginName="+studentNumber+"&Password="+studentPassword+"&&action=GetUserLoginInfo";
		 PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try{
		URL url=new URL(url_str);
        //1.获得连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //2.设置
//        

        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");

        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        conn.setRequestProperty("Accept-Language", "en-US,zh-CN;q=0.8,zh;q=0.5,en;q=0.3");

        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");

        conn.setRequestProperty("Referer", A_URL);

        conn.setRequestProperty("Cookie", cookie);

        conn.setRequestProperty("Connection", "keep-alive");

        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");

        conn.setDoOutput(true);
        conn.setDoInput(true);
//        
        out = new PrintWriter(conn.getOutputStream());
        out.flush();

        in = new BufferedReader(new  InputStreamReader(conn.getInputStream()));
        String line;
        while((line = in.readLine())!=null){
            result +="\n"+line;
        }
        }catch(Exception e){  
        	Logger.getLogger(UserReallyUtilsGetUserInfo.class).error("连接课程中心失败"+e.getMessage());  
        }finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }catch(IOException e){  
            	
            }  
        } 
	}
	
	public static UserReallyInfo getUserInfo(String cookie){
		String B_URL="http://lc.usth.net.cn/G2S/MySpace/PersonalInfo.aspx";
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try{
        URL url = new URL(B_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Cookie",cookie );
        conn.setDoOutput(true);
        conn.setDoInput(true);

        out = new PrintWriter(conn.getOutputStream());
        out.flush();
        in = new BufferedReader(new  InputStreamReader(conn.getInputStream()));
        String line;
        while((line = in.readLine())!=null){
            result +="\n"+line;
        }
        }catch(Exception e){  
           Logger.getLogger(UserReallyUtilsGetUserInfo.class).error("连接课程中心失败");
        }finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }catch(IOException ex){  
            }  
        } 
        //对数据解析
        
        
        
        return parseResult(result);
	}
	public static  UserReallyInfo parseResult(String result){
		UserReallyInfo userReallyInfo=null;
		try {
		
		int index1=0;
		int index2=0;
		//用户名
		  	int userName_index = result.indexOf("ctl00_ContentPlaceHolder1_lblUserName0") +"ctl00_ContentPlaceHolder1_lblUserName0".length();
	        String userName_temp = result.substring(userName_index+2, userName_index+20);
	        index2=userName_temp.indexOf("<");
	        String userName = userName_temp.substring(index1,index2);
	      //学号
	        int studentNumber_index = result.indexOf("ctl00_ContentPlaceHolder1_lblNO0") +"ctl00_ContentPlaceHolder1_lblNO0".length();
	        String studentNumber_temp = result.substring(studentNumber_index + 2, studentNumber_index+20);
	        index2=studentNumber_temp.indexOf("<");
	        String studentNumber=studentNumber_temp.substring(index1,index2);
	      //姓名
	        int reallyName_index = result.indexOf("ctl00_ContentPlaceHolder1_lblName0") +"ctl00_ContentPlaceHolder1_lblName0".length();
	        String reallyName_temp = result.substring(reallyName_index + 2,reallyName_index+8);
	        index2=reallyName_temp.indexOf("<");
	        String reallyName = reallyName_temp.substring(index1,index2);
	      //学院
	        int college_index = result.indexOf("ctl00_ContentPlaceHolder1_lblOrg0") +"ctl00_ContentPlaceHolder1_lblOrg0".length();
	        String college_temp = result.substring(college_index + 2, college_index+13);
	        index2=college_temp.indexOf("<");
	        String college = college_temp.substring(index1,index2);
	      //专业
	        int marjor_index = result.indexOf("ctl00_ContentPlaceHolder1_lblSpeicName0") +"ctl00_ContentPlaceHolder1_lblSpeicName0".length();
	        String marjor_temp = result.substring(marjor_index + 2, marjor_index+13);
	        index2=marjor_temp.indexOf("<");
	        String marjor = marjor_temp.substring(index1,index2);
	        	
	      //年级
	        int classGrade_index = result.indexOf("ctl00_ContentPlaceHolder1_lblStudyTime0") +"ctl00_ContentPlaceHolder1_lblStudyTime0".length();
	        String classGrade = result.substring(classGrade_index + 2, classGrade_index+6);
	       userReallyInfo=new UserReallyInfo();
	        userReallyInfo.setClassGrade(classGrade);
	        userReallyInfo.setCollege(college);
	        userReallyInfo.setMarjor(marjor);
	        userReallyInfo.setReallyName(reallyName);
	        userReallyInfo.setStudentNumber(studentNumber);
		} catch (Exception e) {
			// TODO: handle exception
		}
	        return userReallyInfo;
	}
	public  String getcookies()  {
		try {
		if (this.cookie==null) {
        CookieManager manager=new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        URL url;
		
			url = new URL(A_URL);
		
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.getHeaderFields();
        CookieStore store = manager.getCookieStore();
        String cookies = store.getCookies().toString();
        cookies = cookies.replace("[", "");
        cookies = cookies.replace("]", "");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return this.cookie;
    }
}


