package studentsTakeCoursesGUI;

/*	DAO = Data Access Object
 * 	- a helper class that handles low-level JDBC calls to DB
 *  - popular Java design pattern
 */


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DB from [proj_jdbc] > Enrollment
public class EnrollmentDAO
{

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/proj_jdbc";
	
	static final String USER = "root";
	static final String PASS = "";
	
	Connection myConn = null;
	
	//Constructor
	public EnrollmentDAO() throws Exception{
		
		myConn = DriverManager.getConnection(DB_URL, USER, PASS);
		
		System.out.println("DB connection successful to: " + DB_URL);
	}
	
	public List<Enrollment> getAllEnrollments() throws Exception{
		
		List<Enrollment> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try{			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM Enrollment");
			
			while(myRs.next()){
				Enrollment tempEnrollment = convertRowToEnrollment(myRs);
				list.add(tempEnrollment);
			}
			
			return list;
		}finally{
			close(myStmt, myRs);
		}	
	}
	
	public List<Enrollment> searchEnrollments(String studentId) throws Exception{
		
		List<Enrollment> list = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
	
		try{
//			studentId += "%";
			myStmt = myConn.prepareStatement("SELECT * FROM Enrollment WHERE studentId=?");
			
			myStmt.setString(1, studentId);
			
			myRs = myStmt.executeQuery();
			
			while(myRs.next()){
				Enrollment tempEnrollment = convertRowToEnrollment(myRs);
				list.add(tempEnrollment);
			}
			return list;
			
		}finally{
			close(null, myStmt, myRs);
		}
	}
	
	private Enrollment convertRowToEnrollment(ResultSet myRs) throws SQLException{
		
		int enrollId = myRs.getInt("enrollId");
		int studentId = myRs.getInt("studentId");
		String courseId = myRs.getString("courseId");
		String grade = myRs.getString("grade");	
	
		Enrollment tempEnrollment = new Enrollment(enrollId, studentId, courseId, grade);
	
		return tempEnrollment;
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException{
		if(myRs!=null){
			myRs.close();
		}
		
		if(myStmt!=null){
			
		}
		
		if(myConn!=null){
			myConn.close();
		}
	}
	
	private void close(Statement myStmt, ResultSet myRs) throws SQLException{
		close(null, myStmt, myRs);
	}
	
	public void addEnrollment(Enrollment theEnrollment) throws Exception{
			PreparedStatement myStmt = null;
			
		try{
			//prepare statement
			myStmt = myConn.prepareStatement("INSERT INTO Enrollment"
					+ "(EnrollId, StudentId, CourseId, Grade)"
					+ "VALUES (?, ?, ?, ?)");
			
			//set params
			myStmt.setInt(1, theEnrollment.getEnrollId());
			myStmt.setInt(2, theEnrollment.getStudentId());
			myStmt.setString(3, theEnrollment.getCourseId());
			myStmt.setString(4, theEnrollment.getGrade());
			
			//execute SQL
			myStmt.executeUpdate();
			
		}catch(SQLException se){
			System.out.println("Print error here");
			System.out.println(se.getMessage());
		}finally{
			myStmt.close();
		}
	}
	
	public void updateEnrollment(Enrollment theEnrollment) throws Exception
	{
		PreparedStatement myStmt = null;
		
		try{
			//prepare statement
			myStmt = myConn.prepareStatement("UPDATE Enrollment SET studentId=?, courseId=?, grade=? WHERE enrollId=?");
			
			//set params
			myStmt.setInt(1, theEnrollment.getStudentId());
			myStmt.setString(2, theEnrollment.getCourseId());
			myStmt.setString(3, theEnrollment.getGrade());
			
			myStmt.setInt(4, theEnrollment.getEnrollId());
		
			//execute SQL
			myStmt.executeUpdate();
			
		}finally{
			myStmt.close();
		}
		
	}
	
	public void deleteEnrollment(int enrollId) throws Exception{
		PreparedStatement myStmt = null;
		
		try{
			myStmt = myConn.prepareStatement("DELETE FROM Enrollment WHERE enrollId=?");
		
			myStmt.setInt(1, enrollId);
			
			myStmt.executeUpdate();
			
		}finally{
			myStmt.close();
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		EnrollmentDAO dao = new EnrollmentDAO();
		
		System.out.println(dao.getAllEnrollments());

	}
		
	

}
