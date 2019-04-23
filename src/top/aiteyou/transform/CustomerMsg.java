package top.aiteyou.transform;

import java.io.Serializable;


/**
	*@author :tb
	*@version 2018年5月31日 下午9:15:22
*/

public class CustomerMsg implements Serializable{

	private static final long serialVersionUID = -7300041178891245928L;
	
	private String touser;
	private String msgtype;
	private CustomerMsgImage link;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public CustomerMsgImage getLink() {
		return link;
	}
	public void setLink(CustomerMsgImage link) {
		this.link = link;
	}
	public CustomerMsg(String touser, String msgtype, CustomerMsgImage link) {
		super();
		this.touser = touser;
		this.msgtype = msgtype;
		this.link = link;
	}
	
	
	
}


