package top.aiteyou.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户评论
 * @author tb
 * *@version 2018年3月16日 下午8:57:14
 */
public class GameComment implements Serializable{
	private static final long serialVersionUID = 8844430193823374479L;

	private Integer id;

    private Integer gameId;

    private Integer userId;

    private String content;

    private Integer likecount;

    private Timestamp time;
    
    private Integer status;
    
    //绑定类
    private User user;
    
    
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    
}