package top.aiteyou.service.griefstore;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.griefstore.Letter;
import top.aiteyou.entity.griefstore.LetterImage;
import top.aiteyou.mapper.griefstore.LetterMapper;

/**
	*@author :tb
	*@version 2018年5月3日 上午10:22:39
*/
@Service
public class LetterService {
	@Autowired
	private LetterMapper letterMapper=null;
	@Autowired
	private LetterImageService letterImageService=null;
	private Random random=new Random();
	
	/**
	 * 寄出一份烦恼信  
	 * @param userLetter
	 * @return
	 */
	public int insertOne(Letter letter) {
		List<LetterImage> letterImages=letterImageService.selectAllImage();
		int index=random.nextInt(letterImages.size());
		LetterImage randomImage=letterImages.get(index);
		letter.setImageUrl(randomImage.getUrl());
		return letterMapper.insert(letter);
	}
}

