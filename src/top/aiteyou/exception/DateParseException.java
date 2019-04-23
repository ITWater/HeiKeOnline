package top.aiteyou.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
	*@author :tb
	*@version 2018年3月20日 下午2:43:54
*/
@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR,reason="时间解析错误")
public class DateParseException extends Exception{

	private static final long serialVersionUID = 8610716703652236090L;
	

}


