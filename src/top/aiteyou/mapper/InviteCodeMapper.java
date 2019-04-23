package top.aiteyou.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.aiteyou.entity.InviteCode;

/**
	*@author :tb
	*@version 2018年4月26日 上午11:34:39
*/
@Repository
public interface InviteCodeMapper {
	int insertOne(InviteCode code);

	int insertBatch(List<InviteCode> data);

	InviteCode selectByCode(String code);

	int useCode(InviteCode inviteCode);
}


