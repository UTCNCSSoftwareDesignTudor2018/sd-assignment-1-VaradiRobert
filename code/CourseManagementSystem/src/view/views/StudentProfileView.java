package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import service.response.Response;
import utilities.Observer;
import view.commands.SendEnrollmentRequestCommand;
import view.commands.StudentLogoutCommand;
import view.commands.UnenrollFromCourseCommand;
import view.commands.UpdateProfileCommand;
import view.views.factory.ViewFactory;

@SuppressWarnings("serial")
public class StudentProfileView extends View {
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel profilePanel = new JPanel();
	private JPanel coursesPanel = new JPanel();
	private JPanel groupPanel = new JPanel();
	private JPanel enrollmentPanel = new JPanel();
	private JPanel examPanel = new JPanel();
	private JPanel gradePanel = new JPanel();
	class TabbedPane extends JPanel {
		private JButton logoutButton = new JButton("Logout");
		public TabbedPane() {
			this.setLayout(null);
			this.setVisible(true);
			putComponent(logoutButton, 10, 830, 300, 25);
		}
		public void putComponent(JComponent component, int xPos, int yPos, int width, int height) {
			this.add(component);
			component.setBounds(xPos, yPos, width, height);
			setButtonListener();
		}
		private void setButtonListener() {
			logoutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					Response response = getObserver().execute(new StudentLogoutCommand());
					View nextView = ViewFactory.createView(response);
					nextView.setObserver(getObserver());
					close();
				}
			});
		}
	}
	
	class ProfilePanel extends TabbedPane {
		private JButton updateButton = new JButton("Update Profile");
		private JPasswordField passwordField;
		private JPasswordField passwordAgainField;
		private JTextField emailTextField;
		private JTextField firstNameTextField;
		private JTextField lastNameTextField;
		private JTextField phoneTextField;
		private JTextField addressTextField;
		public ProfilePanel(Student student) {
			super();
			passwordField = new JPasswordField(student.getPassword());
			passwordAgainField = new JPasswordField(student.getPassword());
			emailTextField = new JTextField(student.getEmail());
			firstNameTextField = new JTextField(student.getFirstName());
			lastNameTextField = new JTextField(student.getLastName());
			phoneTextField = new JTextField(student.getPhone());
			addressTextField = new JTextField(student.getAddress());
			putComponent(new JLabel("User Name: "), LABEL_X_POS, 10, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JLabel(student.getUserName()), LABEL_X_POS + LABEL_WIDTH + 10, 10, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Password: "), LABEL_X_POS, 40, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(passwordField, LABEL_X_POS + LABEL_WIDTH + 10, 40, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Password Again: "), LABEL_X_POS, 70, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(passwordAgainField, LABEL_X_POS + LABEL_WIDTH + 10, 70, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Email: "), LABEL_X_POS, 100, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(emailTextField, LABEL_X_POS + LABEL_WIDTH + 10, 100, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("First Name: "), LABEL_X_POS, 130, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(firstNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 130, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Last Name: "), LABEL_X_POS, 160, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(lastNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 160, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Phone: "), LABEL_X_POS, 190, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(phoneTextField, LABEL_X_POS + LABEL_WIDTH + 10, 190, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Address: "), LABEL_X_POS, 220, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(addressTextField, LABEL_X_POS + LABEL_WIDTH + 10, 220, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(updateButton, 50, 250, 300, 25);
			setUpdateButtonListener();
		}
		
		private void setUpdateButtonListener() {
			updateButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					Response response = getObserver().execute(new UpdateProfileCommand(new String(passwordField.getPassword()), new String(passwordAgainField.getPassword()), emailTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText(), addressTextField.getText()));
					View nextView = ViewFactory.createView(response);
					Observer obs = getObserver();
					close();
					nextView.setObserver(obs);
				}
			});
		}
	}
	
	class CoursePanel extends TabbedPane {
		class CourseTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Course Name", "Credits", "Teacher Name", "Teacher User Name"};
			public CourseTableModel(List<Course> courses) {
				int rowCount = courses.size();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = courses.get(i).getName();
					data[i][1] = courses.get(i).getCredits();
					data[i][2] = courses.get(i).getTeacher().getFirstName() + " " + courses.get(i).getTeacher().getLastName();
					data[i][3] = courses.get(i).getTeacher().getUserName(); 
				}
			}
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
			
		}
		private JTable coursesTable;
		public CoursePanel(List<Course> courses) {
			super();
			CourseTableModel ctm = new CourseTableModel(courses);
			coursesTable = new JTable(ctm.data, ctm.columnNames);
			JScrollPane scrollPane = new JScrollPane(coursesTable);
			coursesTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 10, 900, 800);
		}
	}
	
	class EnrollmentsPanel extends TabbedPane {
		class UnenrollButton extends JButton {
			public UnenrollButton() {
				super("Unenroll");
				setButtonListener();
			}
			private void setButtonListener() {
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						int selectedRowNumber = enrollmentsTable.getSelectedRow();
						String courseName = enrollmentsTable.getValueAt(selectedRowNumber, 0).toString();
						Response response = getObserver().execute(new UnenrollFromCourseCommand(courseName));
						View nextView = ViewFactory.createView(response);
						Observer obs = getObserver();
						close();
						nextView.setObserver(obs);
					}
				});
			}
		}
		
		class SendEnrollmentRequestButton extends JButton {
			public SendEnrollmentRequestButton() {
				super("Send Enrollment Request");
				setButtonListener();
			}
			private void setButtonListener() {
				this.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						int selectedRowNumber = enrollmentsTable.getSelectedRow();
						String courseName = enrollmentsTable.getValueAt(selectedRowNumber, 0).toString();
						Response response = getObserver().execute(new SendEnrollmentRequestCommand(courseName));
						View nextView = ViewFactory.createView(response);
						Observer obs = getObserver();
						close();
						nextView.setObserver(obs);
					}
				});
			}
		}
		
		class EnrollmentTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Course Name", "Status", "Teacher Name"};
			public EnrollmentTableModel(List<Enrollment> enrollments) {
				int rowCount = enrollments.size();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					Enrollment e = enrollments.get(i);
					data[i][0] = e.getCourse().getName();
					data[i][1] = e.getStatus();
					data[i][2] = e.getCourse().getTeacher().getFirstName() + " " + e.getCourse().getTeacher().getLastName();
				}
			}
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		}
		
		private JTable enrollmentsTable;
		private UnenrollButton unenrollBtn = new UnenrollButton();
		private SendEnrollmentRequestButton sendEnrollmentRequestBtn = new SendEnrollmentRequestButton();
		private JButton logoutButton = new JButton("Logout");
		
		public EnrollmentsPanel(List<Enrollment> enrollments) {
			super();
			EnrollmentTableModel etm = new EnrollmentTableModel(enrollments);
			enrollmentsTable = new JTable(etm.data, etm.columnNames);
			JScrollPane scrollPane = new JScrollPane(enrollmentsTable);
			enrollmentsTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 10, 700, 800);
			putComponent(unenrollBtn, 400, 810, 300, 25);
			putComponent(sendEnrollmentRequestBtn, 400, 840, 300, 25);
			putComponent(logoutButton, 400, 870, 300, 25);
			logoutButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					Response response = getObserver().execute(new StudentLogoutCommand());
					View nextView = ViewFactory.createView(response);
					Observer obs = getObserver();
					close();
					nextView.setObserver(obs);
				}
			});
		}
	}
	
	class GroupPanel extends TabbedPane {
		class GroupTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Full Name", "Email", "User Name", "Phone", "Address"};
			public GroupTableModel(Group group) {
				int rowCount = group.getStudents().size();
				List<Student> students = group.getStudents();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = students.get(i).getFirstName() + " " + students.get(i).getLastName();
					data[i][1] = students.get(i).getEmail();
					data[i][2] = students.get(i).getUserName();
					data[i][3] = students.get(i).getPhone();
					data[i][4] = students.get(i).getAddress();
				}
			}
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		}
		
		private JTable groupTable;
		
		public GroupPanel(Group group) {
			super();
			putComponent(new JLabel("Group " + group.getNumber()), 10, 10, LABEL_WIDTH, LABEL_HEIGHT);
			GroupTableModel gtm = new GroupTableModel(group);
			groupTable = new JTable(gtm.data, gtm.columnNames);
			JScrollPane scrollPane = new JScrollPane(groupTable);
			groupTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 40, 700, 800);
		}
	}
	
	class GradePanel extends TabbedPane {
		class GradeTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Course", "Date", "Mark"};
			public GradeTableModel(List<Grade> grades) {
				int rowCount = grades.size();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = grades.get(i).getCourse().getName();
					data[i][1] = grades.get(i).getDate().toString();
					data[i][2] = (grades.get(i).getValue() == -1) ? "Not Recorded" : grades.get(i).getValue();
				}
			}
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		}
		
		private JTable gradeTable;
		
		public GradePanel(List<Grade> grades) {
			super();
			GradeTableModel gtm = new GradeTableModel(grades);
			gradeTable = new JTable(gtm.data, gtm.columnNames);
			JScrollPane scrollPane = new JScrollPane(gradeTable);
			gradeTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 40, 700, 800);
		}
	}
	
	class ExamPanel extends TabbedPane {
		class ExamTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Course", "Date"};
			public ExamTableModel(List<Exam> exams) {
				int rowCount = exams.size();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = exams.get(i).getCourse().getName();
					data[i][1] = exams.get(i).getDate().toString();
				}
			}
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		}
		
		private JTable examTable;
		
		public ExamPanel(List<Exam> exams) {
			super();
			ExamTableModel etm = new ExamTableModel(exams);
			examTable = new JTable(etm.data, etm.columnNames);
			JScrollPane scrollPane = new JScrollPane(examTable);
			examTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 40, 700, 800);
		}
	}
	
	public StudentProfileView(Student student, List<Course> courses, List<Enrollment> enrollments, Group group, List<Grade> grades, List<Exam> exams) {
		this(student, courses, enrollments, group, grades, exams, null); 
	}
	
	public StudentProfileView(Student student, List<Course> courses, List<Enrollment> enrollments, Group group, List<Grade> grades, List<Exam> exams, Observer observer) {
		super("Profile", 10, 10, 1000, 1000, observer);
		initializeTabbedPane(student, courses, enrollments, group, grades, exams);
		put(tabbedPane, 0, 0, this.getBounds().width, this.getBounds().height);
	}
	
	private void initializeTabbedPane(Student student, List<Course> courses, List<Enrollment> enrollments, Group group, List<Grade> grades, List<Exam> exams) {
		profilePanel = new ProfilePanel(student);
		coursesPanel = new CoursePanel(courses);
		enrollmentPanel = new EnrollmentsPanel(enrollments);
		groupPanel = new GroupPanel(group);
		examPanel = new ExamPanel(exams);
		gradePanel = new GradePanel(grades);
		tabbedPane.addTab("Profile", profilePanel);
		tabbedPane.addTab("Courses", coursesPanel);
		tabbedPane.addTab("Group", groupPanel);
		tabbedPane.addTab("Enrollments", enrollmentPanel);
		tabbedPane.addTab("Exams", examPanel);
		tabbedPane.addTab("Grades", gradePanel);
	}
}
