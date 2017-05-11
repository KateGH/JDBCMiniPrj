package studentsTakeCoursesGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class EnrollmentSearchApp extends JFrame
{

	private JPanel contentPane;
	private JTextField studentIdTextField;
//	private JButton btnSearch;
	
	private EnrollmentDAO enrollmentDAO;
	private JTable table;
	
	public EnrollmentSearchApp()
	{
		
		try{
			enrollmentDAO = new EnrollmentDAO();
		}catch(Exception exc){
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setTitle("Enrollment Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterStudentId = new JLabel("Enter Student ID");
		panel.add(lblEnterStudentId);
		
		studentIdTextField = new JTextField();
		panel.add(studentIdTextField);
		studentIdTextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Get last name from the text field
				
				//Call DAO and get enrollment for the studentId
				
				//If SID is empty, then get all enrollment
				
				//Print out enrollment
				
				try{
					
					String studentId = studentIdTextField.getText();
					
					List<Enrollment> enrollments = null;
					
					if(studentId!=null && studentId.trim().length()>0){
						enrollments = enrollmentDAO.searchEnrollments(studentId);
						System.out.println("Print out table01");
					}else{
						enrollments = enrollmentDAO.getAllEnrollments();
						System.out.println("Print out table02");
					}
					
					//create the model and update the table i/o print out on console
					EnrollmentTableModel model = new EnrollmentTableModel(enrollments);
					
					table.setModel(model);
					
					for(Enrollment temp : enrollments){
						System.out.println(temp);
					}
				}catch(Exception exc){
					JOptionPane.showMessageDialog(EnrollmentSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
				}
					
			}
		});
		panel.add(btnSearch);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAddRecord = new JButton("Add record");
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// addRecord here by create dialog
				AddEnrollmentDialog dialog = new AddEnrollmentDialog(EnrollmentSearchApp.this, enrollmentDAO);
				
				//show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnAddRecord);
		
		JButton btnUpdateRecord = new JButton("Update record");
		btnUpdateRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//get item - make sure row - get current enrollment - create dialog - show dialog
				//get the selected item
				int row = table.getSelectedRow();
				
				//make sure a row is selected
				if(row<0){
					JOptionPane.showMessageDialog(EnrollmentSearchApp.this, "You must select an enrollment", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//get the current enrollment
				Enrollment tempEnrollment = (Enrollment) table.getValueAt(row, EnrollmentTableModel.OBJECT_COL);
				
				//create dialog
				UpdateEnrollmentDialog dialog = new UpdateEnrollmentDialog(EnrollmentSearchApp.this, enrollmentDAO, tempEnrollment, true);
			
				//show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnUpdateRecord);
		
		JButton btnDeleteRecord = new JButton("Delete record");
		btnDeleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					//get the selected row
					int row = table.getSelectedRow();
					System.out.println("row is: " + row);
					
					//make sure a row is selected
					if(row<0){
						JOptionPane.showMessageDialog(EnrollmentSearchApp.this, "You must select an enrollment", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					//prompt the user
					int response = JOptionPane.showConfirmDialog(EnrollmentSearchApp.this, "Delete this enrollment?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(response != JOptionPane.YES_OPTION){
						return;
					}
					
					//get the current enrollment
					Enrollment tempEnrollment = (Enrollment) table.getValueAt(row, EnrollmentTableModel.OBJECT_COL);
				
					//delete the enrollment
					enrollmentDAO.deleteEnrollment(tempEnrollment.getEnrollId());
				
					// refresh GUI
					refreshEnrollmentView();
					
					// show success message
					JOptionPane.showMessageDialog(EnrollmentSearchApp.this, "Enrollment deleted successfully.", "Enrollment Deleted", JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception exc){
					JOptionPane.showMessageDialog(EnrollmentSearchApp.this, "Error deleting enrollment" + exc.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}finally{
					
				}
			}
		});
		panel_1.add(btnDeleteRecord);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void refreshEnrollmentView()
	{
		try{
			List<Enrollment> enrollments = enrollmentDAO.getAllEnrollments();
			
			//create the model and update the table
			EnrollmentTableModel model = new EnrollmentTableModel(enrollments);
		
			table.setModel(model);
		}catch(Exception exc){
			
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}


/**
 * Launch the application.
 */
public static void main(String[] args)
{
	EventQueue.invokeLater(new Runnable()
	{
		public void run()
		{
			try
			{
				EnrollmentSearchApp frame = new EnrollmentSearchApp();
				frame.setVisible(true);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	});
}


}
