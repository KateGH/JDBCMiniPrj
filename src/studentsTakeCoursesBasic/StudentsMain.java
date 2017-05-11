package studentsTakeCoursesBasic;

import java.sql.*;

public class StudentsMain
{

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/proj_jdbc";
	
	static final String USER = "root";
	static final String PASS = "";
				
	public static void main(String[] args) throws Exception
	{
		Connection conn = null;
		Statement stmt = null;
		
		try{
		
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
					
			String sql = "SELECT * FROM Students as stu INNER JOIN Enrollment as enroll ON stu.StudentId=enroll.StudentId INNER JOIN Courses cos ON enroll.CourseId=cos.CourseId";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int StudentId = rs.getInt("StudentId");
				String lName = rs.getString("lName");
				String fName = rs.getString("fName");
				String telephone = rs.getString("Telephone");
				Date DoB = rs.getDate("DateOfBirth");
				String Major = rs.getString("Major");
				int EnId = rs.getInt("EnrollId");
				String CId = rs.getString("CourseId");
				String grade = rs.getString("Grade");
				String CName = rs.getString("CourseName");
				String Loc = rs.getString("Location");
				
				System.out.print("StudentId: " + StudentId);
				System.out.print(", lName: " + lName);
				System.out.print(", fName: " + fName);
				System.out.print(", Telephone: " + telephone);
				System.out.print(", DateOfBirth: " + DoB);
				System.out.print(", Major: " + Major);
				System.out.print(", EnrollId: " + EnId);
				System.out.print(", CourseId: " + CId);
				System.out.print(", Grade: " + grade);
				System.out.print(", CourseName: " + CName);
				System.out.println(", Location: " + Loc);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		}catch(SQLException se){
			se.printStackTrace();
			System.out.println(se.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			
		}	
	}

}
