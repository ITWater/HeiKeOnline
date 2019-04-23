package top.aiteyou.entity.griefstore;

import java.io.Serializable;
import java.sql.Timestamp;

import top.aiteyou.entity.User;


/**
 * 解忧杂货店-信
	*@author :tb
	*@version 2018年5月3日 上午10:18:46
*/

public class Letter implements Serializable{
	private static final long serialVersionUID = 2144202143975229547L;

	private Integer id;
	
	private Integer fromUserId;
	
	private Integer toUserId;
	
	private Integer replyLetterId;
	
	private String text;
	
	private Timestamp time;
	
	private String imageUrl;//信封图片地址
	
	//绑定类
	private User author;


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getReplyLetterId() {
		return replyLetterId;
	}

	public void setReplyLetterId(Integer replyLetterId) {
		this.replyLetterId = replyLetterId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	
	
	
}


