package top.aiteyou.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.HitMorningImage;

/**
	*@author :tb
	*@version 2018年4月8日 下午1:53:03
*/
@Repository
public interface HitMorningImageMapper {

	List<HitMorningImage> selectRecently();

}


