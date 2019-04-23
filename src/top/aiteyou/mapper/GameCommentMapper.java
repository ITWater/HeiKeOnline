package top.aiteyou.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.GameComment;
import top.aiteyou.transform.GameCommentLike;
@Repository
public interface GameCommentMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(GameComment record);

    int insertSelective(GameComment record);


    GameComment selectByPrimaryKey(Integer id);

    List<GameComment> selectSByGameIdJoinUser(Integer gameId);

    int updateByPrimaryKeySelective(GameComment record);

    int updateByPrimaryKey(GameComment record);

	int addLikeCount(GameCommentLike gameComment);
	
	List<GameComment> selectNotLegalComments(@Param("gameId")Integer gameId);
}