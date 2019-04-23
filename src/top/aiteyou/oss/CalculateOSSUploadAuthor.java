package top.aiteyou.oss;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;

import top.aiteyou.transform.OSSUploadAuthor;

/**
	*@author :tb
	*@version 2018年4月12日 上午11:43:34
*/

public class CalculateOSSUploadAuthor {
	public static OSSUploadAuthor calculate(OSSConfig ossConfig,String dir) throws UnsupportedEncodingException{
		String endpoint = ossConfig.getEndpoint();
        String accessId = ossConfig.getAccessKeyId();
        String accessKey = ossConfig.getAccessKeySecret();
        String bucket =ossConfig.getBucketName();
        String host = "https://" + bucket + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        	long expireTime = 30;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);//大小限制1G
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            //根据当前时间和授权权限计算签名
            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);//计算签名
            
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            OSSUploadAuthor ossUploadAuthor=new OSSUploadAuthor(accessId, encodedPolicy, postSignature, dir, host, String.valueOf(expireEndTime / 1000));
            return ossUploadAuthor;
        }
}


