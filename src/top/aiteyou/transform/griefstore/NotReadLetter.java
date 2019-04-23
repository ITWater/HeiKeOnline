package top.aiteyou.transform.griefstore;

import java.util.List;

import top.aiteyou.entity.griefstore.Letter;

/**
	*@author :tb
	*@version 2018年5月21日 下午8:15:37
*/

public class NotReadLetter {
	private String kindof;
	
	private List<Letter>value;

	public NotReadLetter(String kindof2, List<Letter> userLetters) {
		this.kindof=kindof2;
		this.value=userLetters;
			
	}

	public String getKindof() {
		return kindof;
	}

	public void setKindof(String kindof) {
		this.kindof = kindof;
	}

	public List<Letter> getValue() {
		return value;
	}

	public void setValue(List<Letter> value) {
		this.value = value;
	}
	
	
}


