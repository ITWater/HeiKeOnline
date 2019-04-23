package top.aiteyou.transform;

import java.io.Serializable;

/**
	*@author :tb
	*@version 2018年4月12日 上午11:35:30
*/

public class OSSUploadAuthor implements Serializable{
	
	private static final long serialVersionUID = -7518127177018128808L;

	private String accessid;
	
	private String policy;
	
	private String signature;
	
	private String dir;
	
	private String host;
	
	private String expire;
	
	
	

	public OSSUploadAuthor(String accessid, String policy, String signature, String dir, String host, String expire) {
		super();
		this.accessid = accessid;
		this.policy = policy;
		this.signature = signature;
		this.dir = dir;
		this.host = host;
		this.expire = expire;
	}

	public String getAccessid() {
		return accessid;
	}

	public void setAccessid(String accessid) {
		this.accessid = accessid;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}
	
	
}


