package top.aiteyou.transform;

/**
 *  课程成绩
	*@author :tb
	*@version 2018年5月27日 下午3:39:33
*/

public class ClassGrade {

		
		private String classId;
		
		private String classOrder;
		
		private String className;
		
		private String className_english;
		
		private String credit;
		
		private String classType;
		
		private String classGrade;


		public ClassGrade(String classId, String classOrder, String className, String className_english, String credit,
				String classType, String classGrade) {
			super();
			this.classId = classId;
			this.classOrder = classOrder;
			this.className = className;
			this.className_english = className_english;
			this.credit = credit;
			this.classType = classType;
			this.classGrade = classGrade;
		}

		public String getClassOrder() {
			return classOrder;
		}

		public void setClassOrder(String classOrder) {
			this.classOrder = classOrder;
		}

		public String getClassName_english() {
			return className_english;
		}

		public void setClassName_english(String className_english) {
			this.className_english = className_english;
		}

		public String getClassId() {
			return classId;
		}

		public void setClassId(String classId) {
			this.classId = classId;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getCredit() {
			return credit;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public String getClassType() {
			return classType;
		}

		public void setClassType(String classType) {
			this.classType = classType;
		}

		public String getClassGrade() {
			return classGrade;
		}

		public void setClassGrade(String classGrade) {
			this.classGrade = classGrade;
		}
		
		
	}
