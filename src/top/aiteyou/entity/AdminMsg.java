package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
	*@author :tb
	*@version 2018年5月4日 下午7:47:37
*/

public class AdminMsg implements Serializable{
	private static final long serialVersionUID = 7240204096228230267L;

	private Integer userId; //来自关于哪个用户的
	
	private String content;
	
	private Timestamp time;
	
	private String kindOf;
	
	private String [] imgPaths;

	//绑定类
	private User user;
	
	//关于单个用户时 -socket 
	public AdminMsg(Integer userId, String content ,Timestamp time,String kindOf,User user){
		super();
		this.userId = userId;
		this.content=content;
		this.time = time;
		this.kindOf=kindOf;
		this.user=user;
	}
	//关于单个用户时 -socket 附带图片
	public AdminMsg(Integer userId, String content ,Timestamp time,String kindOf,String [] imgPaths){
		super();
		this.userId = userId;
		this.content=content;
		this.time = time;
		this.kindOf=kindOf;
		this.imgPaths=imgPaths;
	}
	//多个用户时 -template
	public AdminMsg(String content, Timestamp time) {
		super();
		this.content = content;
		this.time = time;
	}

	public AdminMsg() {
	}

	public String[] getImgPaths() {
		return imgPaths;
	}
	public void setImgPaths(String[] imgPaths) {
		this.imgPaths = imgPaths;
	}
	public String getKindOf() {
		return kindOf;
	}
	public void setKindOf(String kindOf) {
		this.kindOf = kindOf;
	}
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}


