package top.aiteyou.entity;

import java.io.Serializable;

/**
 * 用户的真实信息
	*@author :tb
	*@version 2018年5月26日 上午11:58:56
*/

public class UserReallyInfo implements Serializable{
	private static final long serialVersionUID = 2362472428677273408L;

	private Integer id;
	
	private Integer userId;
	
	private String studentNumber;//学号
	
	private String studentPassword;//密码
	
	private String reallyName;//真实姓名
	
	private String classGrade;//年级
	
	private String marjor;//专业
	
	private String college;//学院
	
	private Integer isBand;

	public Integer getIsBand() {
		return isBand;
	}

	public void setIsBand(Integer isBand) {
		this.isBand = isBand;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}

	public String getReallyName() {
		return reallyName;
	}

	public void setReallyName(String reallyName) {
		this.reallyName = reallyName;
	}

	public String getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}

	public String getMarjor() {
		return marjor;
	}

	public void setMarjor(String marjor) {
		this.marjor = marjor;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "UserReallyInfo [id=" + id + ", userId=" + userId + ", studentNumber=" + studentNumber
				+ ", studentPassword=" + studentPassword + ", reallyName=" + reallyName + ", classGrade=" + classGrade
				+ ", marjor=" + marjor + ", college=" + college + "]";
	}
	
	
	
	
}


