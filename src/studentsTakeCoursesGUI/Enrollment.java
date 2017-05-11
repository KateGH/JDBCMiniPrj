package studentsTakeCoursesGUI;

public class Enrollment
{
	private int enrollId;
	private int studentId;
	private String courseId;
	private String grade;
	
	private int maxID = 99;  // create to make auto-increment/auto-shown EnrollID
	
	public Enrollment(){}
	
	public Enrollment(int studentId, String courseId, String grade){
		this(0, studentId, courseId, grade);
	}
	
	public Enrollment(int enrollId, int studentId, String courseId, String grade){
		super();
		this.setEnrollId(enrollId);
		this.setStudentId(studentId);
		this.setCourseId(courseId);
		this.setGrade(grade);
	}

	public int getEnrollId()
	{
		return enrollId;
	}

	public void setEnrollId(int enrollId)
	{
		this.enrollId = enrollId;
	}

	public int getStudentId()
	{
		return studentId;
	}

	public void setStudentId(int studentId)
	{
		this.studentId = studentId;
	}

	public String getCourseId()
	{
		return courseId;
	}

	public void setCourseId(String courseId)
	{
		this.courseId = courseId;
	}

	public String getGrade()
	{
		return grade;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		return String.format("Enrollment [enrollId=%s, studentId=%s, courseId=%s, grade=%s]",
						enrollId, studentId, courseId, grade);
	}
	
}
