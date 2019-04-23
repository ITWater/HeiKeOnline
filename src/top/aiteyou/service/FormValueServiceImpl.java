package top.aiteyou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.aiteyou.entity.FormValue;
import top.aiteyou.mapper.FormValueMapper;

/**
	*@author :tb
	*@version 2018年4月2日 上午10:23:16
*/
@Service
public class FormValueServiceImpl implements FormValueService{
	@Autowired
	private FormValueMapper formValueMapper=null;
	
	@Override
	public int insertOne(FormValue formValue) {
		if (formValue.getAppId()==1) {
			formValue.setDbName("formvalue");			
		}else if(formValue.getAppId()==2){
			formValue.setDbName("formvalue_app2");
		}
		if (formValue.getDbName()==null || formValue.getDbName().equals("")) {
			formValue.setDbName("formvalue");
		}
		
		return formValueMapper.insert(formValue);
	}

}


