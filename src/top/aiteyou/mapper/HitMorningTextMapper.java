package top.aiteyou.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.HitMorningText;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:39:16
*/
@Repository
public interface HitMorningTextMapper {


	List<HitMorningText> selectRecently();

}


