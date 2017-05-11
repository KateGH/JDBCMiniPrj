package studentsTakeCoursesGUI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EnrollmentTableModel extends AbstractTableModel {
	
	//private column name
	public static final int OBJECT_COL = -1; //?? what is this for?
	private static final int ENROLLID_COL = 0;
	private static final int STUDENTID_COL = 1;
	private static final int COURSEID_COL = 2;
	private static final int GRADE_COL = 3;

	private String[] columnNames = {"EnrollId", "StudentId", "CourseId", "Grade"};
	private List<Enrollment> enrollments;
	
	//constructor
	public EnrollmentTableModel(List<Enrollment> theEnrollments){
		enrollments = theEnrollments;
	}
	
	/*Override 5 methods below:-
	1.getColumnCount()
	2.getRowCount()
	3.getColumnName(int col)
	4.getColumnClass(int col)
	5.getValueAt(int row, int col)
	*/
	
	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}
	
	@Override
	public int getRowCount()
	{
		return enrollments.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public Object getValueAt(int row, int col)
	{
		Enrollment tempEnrollments = enrollments.get(row);

		switch (col) {
		case ENROLLID_COL:
			return tempEnrollments.getEnrollId();
		case STUDENTID_COL:
			return tempEnrollments.getStudentId();
		case COURSEID_COL:
			return tempEnrollments.getCourseId();
		case GRADE_COL:
			return tempEnrollments.getGrade();
		case OBJECT_COL:
			return tempEnrollments;	
		default:
			return tempEnrollments.getEnrollId();
		}
	}

}
