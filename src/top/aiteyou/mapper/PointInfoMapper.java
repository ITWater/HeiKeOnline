package top.aiteyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.PointInfo;
@Repository
public interface PointInfoMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(PointInfo record);

    int insertSelective(PointInfo record);


    PointInfo selectByPrimaryKey(Integer id);

    List<PointInfo> selectByGameIdJoinMember(@Param("gameId")Integer gameId);

    int updateByPrimaryKeySelective(PointInfo record);

    int updateByPrimaryKey(PointInfo record);
}