package studentsTakeCoursesGUI;

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
import java.math.BigDecimal;

public class UpdateEnrollmentDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField enrollIdTextField;
	private JTextField studentIdTextField;
	private JTextField courseIdTextField;
	private JTextField gradeTextField;
	
	private EnrollmentDAO enrollmentDAO;
	private EnrollmentSearchApp enrollmentSearchApp;
	
	private Enrollment previousEnrollment = null;
	private boolean updateMode = false;
	
	//Constructors
	
	public UpdateEnrollmentDialog(EnrollmentSearchApp theEnrollmentSearchApp, EnrollmentDAO theEnrollmentDAO){
		this(theEnrollmentSearchApp, theEnrollmentDAO, null, false);
	}
	
	public UpdateEnrollmentDialog(EnrollmentSearchApp theEnrollmentSearchApp, EnrollmentDAO theEnrollmentDAO, Enrollment thePreviousEnrollment, boolean theUpdateMode){
		this();
		enrollmentSearchApp = theEnrollmentSearchApp;
		enrollmentDAO = theEnrollmentDAO;
		previousEnrollment = thePreviousEnrollment;
		updateMode = theUpdateMode;
		
		if(updateMode){
			setTitle("Update Enrollment");
			
			populateGui(previousEnrollment);
		}
	}
	
	private void populateGui(Enrollment theEnrollment)
	{	
		enrollIdTextField.setText("" + theEnrollment.getEnrollId()); //!! I solve it with insert "" to make int => string
		studentIdTextField.setText("" +theEnrollment.getStudentId());
		courseIdTextField.setText(theEnrollment.getCourseId());
		gradeTextField.setText(theEnrollment.getGrade());
		
	}
		
	/**
	 * Create the dialog.
	 */
	public UpdateEnrollmentDialog()
	{
		setTitle("Update Enrollment");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("60px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("364px:grow"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("26px"),
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
			JLabel lblNewLabel = new JLabel("StudentId");
			contentPanel.add(lblNewLabel, "2, 4, right, center");
		}
		{
			studentIdTextField = new JTextField();
			contentPanel.add(studentIdTextField, "4, 4, fill, top");
			studentIdTextField.setColumns(10);
		}
		{
			JLabel lblCourseid = new JLabel("CourseId");
			contentPanel.add(lblCourseid, "2, 6, right, center");
		}
		{
			courseIdTextField = new JTextField();
			contentPanel.add(courseIdTextField, "4, 6, fill, top");
			courseIdTextField.setColumns(10);
		}
		{
			JLabel lblGrade = new JLabel("Grade");
			contentPanel.add(lblGrade, "2, 8, right, center");
		}
		{
			gradeTextField = new JTextField();
			contentPanel.add(gradeTextField, "4, 8, fill, top");
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
		// get-save-close-refresh-show
		
		String studentIdStr = studentIdTextField.getText();
		int studentId = Integer.parseInt(studentIdStr);
		
		String courseId = courseIdTextField.getText();
		String grade = gradeTextField.getText();
		
		Enrollment tempEnrollment = null;
		
		if(updateMode){
			
			tempEnrollment = previousEnrollment;
			
			tempEnrollment.setStudentId(studentId);
			tempEnrollment.setCourseId(courseId);
			tempEnrollment.setGrade(grade);
		}else{
			tempEnrollment = new Enrollment(studentId, courseId, grade);
		}
		
		try{
			// save-close-refresh-show
			
			//save to DB		
			if(updateMode){
				enrollmentDAO.updateEnrollment(tempEnrollment);
			}else{
				enrollmentDAO.addEnrollment(tempEnrollment);
			}
			
			//close dialog
			setVisible(false);
			dispose();
			
			//refresh Gui list
			enrollmentSearchApp.refreshEnrollmentView();
			
			//show success message
			JOptionPane.showMessageDialog(enrollmentSearchApp, "Enrollment updated successfully", "Enrollment added", JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception exc){
			
			JOptionPane.showMessageDialog(enrollmentSearchApp, "Error saving enrollment: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

		}	
	}
}
