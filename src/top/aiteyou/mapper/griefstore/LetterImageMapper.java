package top.aiteyou.mapper.griefstore;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.griefstore.LetterImage;

/**
	*@author :tb
	*@version 2018年5月24日 下午4:22:42
*/
@Repository
public interface LetterImageMapper {
	 List<LetterImage> selectAllImage();
}


