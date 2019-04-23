package top.aiteyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.aiteyou.entity.Season;

public interface SeasonMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Season record);

    int insertSelective(Season record);


    Season selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(Season record);

    int updateByPrimaryKey(Season record);
}