package top.aiteyou.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import top.aiteyou.entity.UserReallyInfo;
import top.aiteyou.transform.ClassGrade;


/**
	*@author :tb
	*@version 2018年5月26日 下午12:40:54
*/

public class UserReallyUtilsGetClassGrade {
	
	private String cookie;
	public static String A_URL="http://60.219.165.24/loginAction.do";
	
	
	
	public static void getSession(String cookie,String studentNumber,String studentPassword){
	
				
	String url_str=A_URL+"?mm="+studentPassword+"&zjh="+studentNumber;
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
//        out = new PrintWriter(conn.getOutputStream());
//        out.write(param);
//        out.flush();

        in = new BufferedReader(new  InputStreamReader(conn.getInputStream()));
        String line;
        while((line = in.readLine())!=null){
            result +="\n"+line;
        }
        }catch(Exception e){  
        	Logger.getLogger(UserReallyUtilsGetClassGrade.class).error("连接课程中心失败"+e.getMessage());  
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
	
	public static List<ClassGrade> getClassGrade(String cookie){
		List<ClassGrade> classGrades=null;
		String B_URL="http://60.219.165.24/bxqcjcxAction.do";
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
        in = new BufferedReader(new  InputStreamReader(conn.getInputStream(),"GB2312"));
        String line;
        while((line = in.readLine())!=null){
            result +="\n"+line;
        }
        }catch(Exception e){  
           Logger.getLogger(UserReallyUtilsGetClassGrade.class).error("连接课程中心失败");
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
        
        try {
			classGrades= parseData(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
       
        return classGrades;
	}
	public static List<ClassGrade> parseData(String result) throws UnsupportedEncodingException{
		UserReallyInfo userReallyInfo=null;
		Pattern p = Pattern.compile("<td align=\"center\">(\n(\t*))*(.*?)(\n(\t*))*(\\s*)</td>");
		Matcher m = p.matcher(result);
		List<String> list=new ArrayList<String>();
		List<ClassGrade> classGrades=new ArrayList<ClassGrade>();
		
		while(m.find()){
			String value=m.group().replace("<td align=\"center\">", "");
			value=value.replace("</td>", "");
			String item =value.trim();
			item=new String(item.getBytes(),"UTF-8");
			list.add(item);
		}
		int j=0;
		for(int i=0;j<(list.size()/7);i=i+7){
			String classId=list.get(i);
			String classOrder=list.get(i+1);
			String className=list.get(i+2);
			String className_english=list.get(i+3);
			String credit=list.get(i+4);
			String classType=list.get(i+5);
			String classGrade=list.get(i+6);
			ClassGrade classGradeObj=new ClassGrade(classId, classOrder, className, className_english, credit, classType, classGrade);
			classGrades.add(classGradeObj);
			j++;
		}
		return classGrades;
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
class SchoolObject{
	private String mm;
	
	private String zjh;

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}
	
	
}


