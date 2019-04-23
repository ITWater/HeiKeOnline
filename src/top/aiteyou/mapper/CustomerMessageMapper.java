package top.aiteyou.mapper;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.CustomerMessage;

/**
	*@author :tb
	*@version 2018年5月31日 下午8:39:14
*/
@Repository
public interface CustomerMessageMapper {
	int insert(CustomerMessage customerMessage);
}


