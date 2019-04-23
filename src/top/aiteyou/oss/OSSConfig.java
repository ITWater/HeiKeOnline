package top.aiteyou.oss;

import java.io.IOException;

/**
	*@author :tb
	*@version 2018年4月12日 上午11:26:22
*/

public class OSSConfig {
	private  String endpoint;      
    private  String accessKeyId;   
    private  String accessKeySecret;    
    private  String bucketName;     
    private  String ossUrl; //oss访问域名
    
    //单例
    private  static OSSConfig instance=new OSSConfig();
    
    public static OSSConfig getInstance(){
    	return instance;
    }
    
    private OSSConfig() {  
        try {
            this.endpoint = SystemOSSConfig.getConfigResource("endpoint");
			this.bucketName = SystemOSSConfig.getConfigResource("bucketName");
			this.accessKeyId = SystemOSSConfig.getConfigResource("accessKeyId");
			this.accessKeySecret = SystemOSSConfig.getConfigResource("accessKeySecret");
			this.ossUrl = SystemOSSConfig.getConfigResource("url");
		} catch (IOException e) {
            e.printStackTrace();  
        }  
    }


	public String getOssUrl() {
		return ossUrl;
	}


	public void setOssUrl(String ossUrl) {
		this.ossUrl = ossUrl;
	}


	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
}

