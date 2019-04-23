package top.aiteyou.transform;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
	*@author :tb
	*@version 2018年5月31日 下午9:19:43
*/

public class CustomerMsgFrom implements Serializable{
	private static final long serialVersionUID = -2034262693971223246L;
	@JsonProperty("ToUserName")
	private String toUserName;
	@JsonProperty("FromUserName")
	private String fromUserName;
	@JsonProperty("CreateTime")
	private String createTime;
	@JsonProperty("MsgType")
	private String msgType;
	@JsonProperty("Content")
	private String content;
	@JsonProperty("MsgId")
	private String msgId;
	
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}

