package top.aiteyou.service.griefstore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.griefstore.LetterImage;
import top.aiteyou.mapper.griefstore.LetterImageMapper;

/**
	*@author :tb
	*@version 2018年5月24日 下午4:27:25
*/
@Service
public class LetterImageService {
	@Autowired
	private LetterImageMapper letterImageMapper=null;
	/**
	 * 查询所有的解忧信信封图 缓存
	 * @return
	 */
	@Cacheable(value="griefLetterImage",key="'griefLetterImageAll'")
	public List<LetterImage> selectAllImage(){
		return letterImageMapper.selectAllImage();
	}
}


