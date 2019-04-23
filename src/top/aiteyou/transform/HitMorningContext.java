package top.aiteyou.transform;

import java.sql.Timestamp;
import java.util.List;

import top.aiteyou.entity.HitMorningImage;
import top.aiteyou.entity.HitMorningText;
import top.aiteyou.entity.User;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:09:37
*/

public class HitMorningContext {
	private Integer number;
	
	private HitMorningText text;
	
	private HitMorningImage image;
	
	private Timestamp time;
	

	public HitMorningContext(Integer signNumber, HitMorningText hitMorningText, HitMorningImage hitMorningImage) {
		this.number=signNumber;
		this.text=hitMorningText;
		this.image=hitMorningImage;
	}
	
	
	
	
	public HitMorningContext() {
		super();
	}




	public Timestamp getTime() {
		return time;
	}




	public void setTime(Timestamp time) {
		this.time = time;
	}




	





	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}


	public HitMorningImage getImage() {
		return image;
	}

	public void setImage(HitMorningImage image) {
		this.image = image;
	}

	public HitMorningText getText() {
		return text;
	}

	public void setText(HitMorningText text) {
		this.text = text;
	}
	
	
}


