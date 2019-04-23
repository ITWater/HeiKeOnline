package top.aiteyou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 赛季
 * @author tb
 * *@version 2018年3月16日 下午9:10:46
 */
public class Season implements Serializable{
	private static final long serialVersionUID = 7351850981491117899L;

	private Integer id;

    private String name;

    private String introduce;
    
    private String log;
    
    
    private List<Game> joingames;

    
	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public List<Game> getJoingames() {
		return joingames;
	}

	public void setJoingames(List<Game> joingames) {
		this.joingames = joingames;
	}

	@Override
	public String toString() {
		return "Season [id=" + id + ", name=" + name + ", introduce=" + introduce + ", joingames=" + joingames + "]";
	}
    
    
}