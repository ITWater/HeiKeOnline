package top.aiteyou.mapper;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
	*@author :tb
	*@version 2018年5月6日 下午2:50:54
*/

public class TestM {
	public static void main(String[] args) {
		int minu=4900;
		float diff=(minu/(24*60.0f));
		NumberFormat  formater  =DecimalFormat.getInstance();
		formater.setMaximumFractionDigits(1);  
		System.out.println(formater.format(diff)+"doa");
	}
}


