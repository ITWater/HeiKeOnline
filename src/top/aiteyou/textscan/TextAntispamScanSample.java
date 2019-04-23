package top.aiteyou.textscan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20170112.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.*;

/**
 * Created by hyliu on 16/3/2.
 * 文本检测
 */
public class TextAntispamScanSample extends BaseSample {
	/**
	 * 用于 对评论的内容进行初过滤
	 * true -违规
	 * false-正常
	 * @param content
	 * @return
	 */
	private static TextScanRequest textScanRequest=initTextScanRequest();
	private static TextScanRequest initTextScanRequest() {
		 TextScanRequest textScanRequest = new TextScanRequest();
	        textScanRequest.setAcceptFormat(FormatType.JSON); // 指定api返回格式
	        textScanRequest.setContentType(FormatType.JSON);
	        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST); // 指定请求方法
	        textScanRequest.setEncoding("UTF-8");
	        textScanRequest.setRegionId(regionId);
	        /**
	         * 请务必设置超时时间
	         */
	        textScanRequest.setConnectTimeout(3000);
	        textScanRequest.setReadTimeout(6000);
	        return textScanRequest;
	}
    public static boolean scanText(String content)  {
        try {
    	
    	IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(getEndPointName(), regionId, "Green", getDomain());

        IAcsClient client = new DefaultAcsClient(profile);

        
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task1 = new LinkedHashMap<String, Object>();
        task1.put("dataId", UUID.randomUUID().toString());
        task1.put("content", content);
        
        tasks.add(task1);

        JSONObject data = new JSONObject();
        data.put("scenes", Arrays.asList("antispam"));
        data.put("tasks", tasks);

        textScanRequest.setContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);

       
            HttpResponse httpResponse = client.doAction(textScanRequest);
            if(httpResponse.isSuccess()){
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getContent(), "UTF-8"));
                if (200 == scrResponse.getInteger("code")) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if(200 == ((JSONObject)taskResult).getInteger("code")){
                            JSONArray sceneResults = ((JSONObject)taskResult).getJSONArray("results");
                            for (Object sceneResult : sceneResults) {
                                String scene = ((JSONObject)sceneResult).getString("scene");
                                String suggestion = ((JSONObject)sceneResult).getString("suggestion");
                                //根据scene和suggetion做相关的处理
                                if ("block".equals(suggestion)) {
									return true;
								}
                                return false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        	return false;
		}
		return false;
    }
   
}
