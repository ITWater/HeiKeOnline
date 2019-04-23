package top.aiteyou.service.schoolarticle;

import java.util.ArrayList;
import java.util.List;

import top.aiteyou.entity.schoolArticle.ArticlePicture;

/**
	*@author :tb
	*@version 2018年4月25日 上午9:48:27
*/

public class TestM {
	public static void main(String[] args) {
		List<ArticlePicture> pictures=new ArrayList<ArticlePicture>();
		System.out.println(pictures.size());
		for (ArticlePicture articlePicture : pictures) {
			System.out.println("coming");
		}
	}
}


