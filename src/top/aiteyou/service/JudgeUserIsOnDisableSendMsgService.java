package top.aiteyou.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import top.aiteyou.controller.GameController;
import top.aiteyou.entity.DisableSendMsg;
import top.aiteyou.mapper.DisableSendMsgMapper;
import top.aiteyou.transform.DisableInfo;

/**
	*@author :tb
	*@version 2018年5月6日 下午2:13:58
*/
@Service
public class JudgeUserIsOnDisableSendMsgService {
	@Autowired
	private  DisableSendMsgMapper disableSendMsgMapper=null;
	private static Calendar calendar=Calendar.getInstance();
	private static NumberFormat  formater  =DecimalFormat.getInstance();

	static{
		formater.setMaximumFractionDigits(1);  //保留1位小数
	}
	
	@Cacheable(value="disableUser",key="'diableUserId'+#userId")
	public  DisableInfo judgeUserIsDisableSendMsg(Integer userId){
		DisableInfo disableInfo=new DisableInfo(false, "");
		
		DisableSendMsg disableSendMsg=disableSendMsgMapper.selectOneUser(userId);
		if (disableSendMsg!=null ) {
			if (disableSendMsg.getStatus()==1) {
				
				disableInfo.setDisable(true);
				
				//计算还有多长时间解封
				int disableMinutes=disableSendMsg.getMinutes();
				Timestamp thatDayOfDisable=disableSendMsg.getTime();
				calendar.setTimeInMillis(thatDayOfDisable.getTime());
				int minu1=calendar.get(Calendar.MINUTE);
				int ableSendMsgTimeMiu=minu1+disableMinutes;//解封那时候的分钟数
				Date now=new Date();
				calendar.setTime(now);
				int nowMinu=calendar.get(Calendar.MINUTE);//现在的分钟数
				int diffvalue=ableSendMsgTimeMiu-nowMinu;
				String unit="";
				float diff=0.0f;
				if (diffvalue >(24*60)) {
					unit="天";
					diff=(diffvalue/(24*60.0f));
				}else {
					unit="小时";
					diff=(diffvalue/(60.0f));
				}
				
				String diff_str=formater.format(diff);
				disableInfo.setDiffTime(diff_str+unit);
				return disableInfo;
				
			}
		}
		
		return disableInfo;
	}
}


