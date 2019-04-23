package top.aiteyou.mapper.griefstore;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.griefstore.Letter;


/**
	*@author :tb
	*@version 2018年5月3日 上午10:23:54
*/
@Repository
public interface LetterMapper {
	int insert(Letter letter);
}


