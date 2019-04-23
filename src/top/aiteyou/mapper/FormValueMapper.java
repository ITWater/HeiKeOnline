package top.aiteyou.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import top.aiteyou.entity.FormValue;

/**
	*@author :tb
	*@version 2018年4月2日 上午10:15:14
*/
@Repository
public interface FormValueMapper {
	int insert(FormValue formValue);

	void deleteOutTimeFormValue(@Param("time")String before_str);
	
	FormValue selectOneOld(@Param("dbName")String formValueDbName,@Param("userId")int userId);

	void deleteUse(FormValue formValue);
}


