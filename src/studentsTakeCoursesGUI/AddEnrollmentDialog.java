package studentsTakeCoursesGUI;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddEnrollmentDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField enrollIdTextField;
	private JTextField studentIdTextField;
	private JTextField courseIdTextField;
	private JTextField gradeTextField;

	private EnrollmentSearchApp enrollmentSearchApp;
	private EnrollmentDAO enrollmentDAO;
	
	
	
	public AddEnrollmentDialog(EnrollmentSearchApp theEnrollmentSearchApp, EnrollmentDAO theEnrollmentDAO){
		this();
		enrollmentSearchApp = theEnrollmentSearchApp;
		enrollmentDAO = theEnrollmentDAO;
	}
	
	/**
	 * Create the dialog.
	 */
	public AddEnrollmentDialog()
	{

		setTitle("Add Record");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		{
			JLabel lblEnrollid = new JLabel("EnrollId");
			contentPanel.add(lblEnrollid, "2, 2, right, default");
		}
		{
			enrollIdTextField = new JTextField();
			contentPanel.add(enrollIdTextField, "4, 2, fill, default");
			enrollIdTextField.setColumns(10);
		}
		{
			JLabel lblStudentid = new JLabel("StudentId");
			contentPanel.add(lblStudentid, "2, 4, right, default");
		}
		{
			studentIdTextField = new JTextField();
			contentPanel.add(studentIdTextField, "4, 4, fill, default");
			studentIdTextField.setColumns(10);
		}
		{
			JLabel lblCourseid = new JLabel("CourseId");
			contentPanel.add(lblCourseid, "2, 6, right, default");
		}
		{
			courseIdTextField = new JTextField();
			contentPanel.add(courseIdTextField, "4, 6, fill, default");
			courseIdTextField.setColumns(10);
		}
		{
			JLabel lblGrade = new JLabel("Grade");
			contentPanel.add(lblGrade, "2, 8, right, default");
		}
		{
			gradeTextField = new JTextField();
			contentPanel.add(gradeTextField, "4, 8, fill, default");
			gradeTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
							saveEnrollment();
						
					}

				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						setVisible(false);
						dispose();
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	protected void saveEnrollment() 
	{	
		// get - save - close - refresh - show
		// get the enrollment info from GUI
		String enrollIdStr = enrollIdTextField.getText(); // always .getText becoz info get from input field
		int enrollId = Integer.parseInt(enrollIdStr);
		
		String studentIdStr = studentIdTextField.getText();
		int studentId = Integer.parseInt(studentIdStr);
		
		String courseId = courseIdTextField.getText();
		String grade = gradeTextField.getText();
		
		Enrollment tempEnrollment = new Enrollment(enrollId, studentId, courseId, grade);

		try{
			
			//save to DB
			enrollmentDAO.addEnrollment(tempEnrollment);
					
			//close dialog
			setVisible(false);
			dispose();
			
			//refresh GUI list
			enrollmentSearchApp.refreshEnrollmentView();
			
			//show success message
			JOptionPane.showMessageDialog(enrollmentSearchApp, "Enrollment added successfully", "Enrollment added", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(Exception exc){
			JOptionPane.showConfirmDialog(enrollmentSearchApp, "Error saving enrollment: " + exc.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);	
		}	
	}
}
