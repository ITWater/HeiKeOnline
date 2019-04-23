package top.aiteyou.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.Member;
@Repository
public interface MemberMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    int insertSelective(Member record);


    Member selectByPrimaryKey(Integer id);

    List<Member> selectByTeamId(@Param("teamId") int teamId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}