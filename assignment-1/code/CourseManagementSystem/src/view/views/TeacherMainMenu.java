package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import business.business_logic.EnrollmentBLL;
import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Group;
import persistence.entities.Student;
import persistence.entities.Teacher;
import service.response.Response;
import utilities.Observer;
import view.commands.AcceptEnrollmentRequestCommand;
import view.commands.DeclineEnrollmentRequestCommand;
import view.commands.GradeStudentCommand;
import view.commands.RemoveStudentFromCourseCommand;
import view.commands.TeacherLogoutCommand;
import view.views.factory.ViewFactory;

@SuppressWarnings("serial")
public class TeacherMainMenu extends View {
	private JTabbedPane tabbedPane = new JTabbedPane();
	class TabbedPane extends JPanel {
		private JButton logoutButton = new JButton("Logout");
		public TabbedPane() {
			this.setLayout(null);
			this.setVisible(true);
			putComponent(logoutButton, 10, 850, 300, 25);
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
					Response response = getObserver().execute(new TeacherLogoutCommand());
					View nextView = ViewFactory.createView(response);
					nextView.setObserver(getObserver());
					close();
				}
			});
		}
	}
	class ProfilePanel extends TabbedPane {
		public ProfilePanel(Teacher teacher) {
			super();
			putComponent(new JLabel("User Name: "), LABEL_X_POS, 10, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JLabel(teacher.getUserName()), LABEL_X_POS + LABEL_WIDTH + 10, 10, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Password: "), LABEL_X_POS, 40, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JPasswordField(teacher.getPassword()), LABEL_X_POS + LABEL_WIDTH + 10, 40, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Password Again: "), LABEL_X_POS, 70, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JPasswordField(teacher.getPassword()), LABEL_X_POS + LABEL_WIDTH + 10, 70, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Email: "), LABEL_X_POS, 100, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JTextField(teacher.getEmail()), LABEL_X_POS + LABEL_WIDTH + 10, 100, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("First Name: "), LABEL_X_POS, 130, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JTextField(teacher.getFirstName()), LABEL_X_POS + LABEL_WIDTH + 10, 130, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Last Name: "), LABEL_X_POS, 160, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JTextField(teacher.getLastName()), LABEL_X_POS + LABEL_WIDTH + 10, 160, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
			putComponent(new JLabel("Phone: "), LABEL_X_POS, 190, LABEL_WIDTH, LABEL_HEIGHT);
			putComponent(new JTextField(teacher.getPhone()), LABEL_X_POS + LABEL_WIDTH + 10, 190, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
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
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
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
			tabbedPane.addTab("Group " + group.getNumber(), this);
		}
	}
	
	class CoursePanel extends TabbedPane {
		class CourseTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Full Name", "Email", "User Name", "Phone", "Address", "Grade"};
			public CourseTableModel(Course course, Map<String, Double> grades) {
				int rowCount = course.getEnrolledStudents().size();
				List<Student> students = course.getEnrolledStudents();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = students.get(i).getFirstName() + " " + students.get(i).getLastName();
					data[i][1] = students.get(i).getEmail();
					data[i][2] = students.get(i).getUserName();
					data[i][3] = students.get(i).getPhone();
					data[i][4] = students.get(i).getAddress();
					Double grade = null;
					if(grades != null) {
						grade = grades.get(students.get(i).getUserName());
					}
					data[i][5] = (grade != null && grade != -1.0) ? grade : null;
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
			
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 5 && data[row][column] == null) {
					return true;
				} else {
					return false;
				}
			}
		}
		private String courseName;
		private JTable courseTable;
		private JButton deleteStudentButton = new JButton("Remove from course");
		private JButton gradeStudentButton = new JButton("Grade Student");
		private void setDeleteStudentButtonListener() {
			deleteStudentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					int selectedRow = courseTable.getSelectedRow();
					if(selectedRow == -1) {
						JOptionPane.showMessageDialog(rootPane, "No row has been selected!", "Error!", JOptionPane.ERROR_MESSAGE);
					} else {
						String userName = courseTable.getValueAt(selectedRow, 2).toString();
						Response response = getObserver().execute(new RemoveStudentFromCourseCommand(userName, courseName));
						View nextView = ViewFactory.createView(response);
						Observer observer = getObserver();
						close();
						nextView.setObserver(observer);
					}
				}
			});
		}
		private void setGradeStudentButtonListener() {
			gradeStudentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					int selectedRow = courseTable.getSelectedRow();
					String userName = courseTable.getValueAt(selectedRow, 2).toString();
					Object gradeObject = courseTable.getValueAt(selectedRow, 5);
					if(selectedRow == -1) {
						JOptionPane.showMessageDialog(rootPane, "You should select one row!", "Error", JOptionPane.ERROR_MESSAGE);
					} else if(gradeObject == null) {
						JOptionPane.showMessageDialog(rootPane, "The grade field should not be left null!", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						String gradeString = gradeObject.toString();
						Response response = getObserver().execute(new GradeStudentCommand(userName, courseName, Double.parseDouble(gradeString)));
						View nextView = ViewFactory.createView(response);
						Observer observer = getObserver();
						close();
						nextView.setObserver(observer);
					}
				}
			});
		}
		public CoursePanel(Course course, Map<String, Double> grades) {
			super();
			this.courseName = course.getName();
			CourseTableModel ctm = new CourseTableModel(course, grades);
			courseTable = new JTable(ctm.data, ctm.columnNames);
			JScrollPane scrollPane = new JScrollPane(courseTable);
			courseTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 40, 700, 800);
			putComponent(deleteStudentButton, 400, 850, 300, 25);
			putComponent(gradeStudentButton, 400, 880, 300, 25);
			tabbedPane.addTab(course.getName(), this);
			setDeleteStudentButtonListener();
			setGradeStudentButtonListener();
		}
	}
	
	class EnrollmentsPanel extends TabbedPane {
		class EnrollmentsTableModel extends AbstractTableModel {
			private Object[][] data;
			private String[] columnNames = {"Course Name", "Student Name", "Student User Name", "Status"};
			public EnrollmentsTableModel(List<Enrollment> enrollments) {
				List<Enrollment> enrollmentRequests = enrollments.stream().filter(e -> ((Enrollment)e).getStatus().equals(EnrollmentBLL.STATUS_REQUEST)).collect(Collectors.toList());
				int rowCount = enrollmentRequests.size();
				data = new Object[rowCount][columnNames.length];
				for(int i = 0; i < rowCount; i++) {
					data[i][0] = enrollmentRequests.get(i).getCourse().getName();
					data[i][1] = enrollmentRequests.get(i).getStudent().getFirstName() + " " + enrollmentRequests.get(i).getStudent().getLastName();
					data[i][2] = enrollmentRequests.get(i).getStudent().getUserName();
					data[i][3] = enrollmentRequests.get(i).getStatus();
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
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		}
		private JButton acceptEnrollmentButton = new JButton("Accept Enrollment");
		private JButton declineEnrollmentButton = new JButton("Decline Enrollment");
		private JTable enrollmentsTable;
		private void setAcceptEnrollmentButtonListener() {
			acceptEnrollmentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					int selectedRow = enrollmentsTable.getSelectedRow();
					String studentUserName = enrollmentsTable.getValueAt(selectedRow, 2).toString();
					String courseName = enrollmentsTable.getValueAt(selectedRow, 0).toString();
					Response response = getObserver().execute(new AcceptEnrollmentRequestCommand(studentUserName, courseName));
					View nextView = ViewFactory.createView(response);
					Observer obs = getObserver();
					close();
					nextView.setObserver(obs);
				}
			});
		}
		private void setDeclineEnrollmentButtonListener() {
			declineEnrollmentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					int selectedRow = enrollmentsTable.getSelectedRow();
					if(selectedRow == -1) {
						JOptionPane.showMessageDialog(rootPane, "No row has been selected!", "Error!", JOptionPane.ERROR_MESSAGE);
					} else {
						String studentUserName = enrollmentsTable.getValueAt(selectedRow, 2).toString();
						String courseName = enrollmentsTable.getValueAt(selectedRow, 0).toString();
						Response response = getObserver().execute(new DeclineEnrollmentRequestCommand(studentUserName, courseName));
						View nextView = ViewFactory.createView(response);
						Observer obs = getObserver();
						close();
						nextView.setObserver(obs);
					}
				}
			});
		}
		
		private void setActionListeners() {
			setAcceptEnrollmentButtonListener();
			setDeclineEnrollmentButtonListener();
		}
		public EnrollmentsPanel(List<Enrollment> enrollments) {
			super();
			EnrollmentsTableModel etm = new EnrollmentsTableModel(enrollments);
			enrollmentsTable = new JTable(etm.data, etm.columnNames);
			JScrollPane scrollPane = new JScrollPane(enrollmentsTable);
			enrollmentsTable.setFillsViewportHeight(true);
			putComponent(scrollPane, 10, 40, 700, 800);
			putComponent(acceptEnrollmentButton, 400, 850, 300, 25);
			putComponent(declineEnrollmentButton, 400, 880, 300, 25);
			tabbedPane.addTab("Enrollment requests", this);
			setActionListeners();
		}
	}
	
	public TeacherMainMenu(Teacher teacher, List<Group> groups, List<Enrollment> enrollments, List<Course> courses, Map<String, Map<String, Double>> grades) {
		this(teacher, groups, enrollments, courses, grades, null);
	}
	
	public TeacherMainMenu(Teacher teacher, List<Group> groups, List<Enrollment> enrollments, List<Course> courses, Map<String, Map<String, Double>> grades, Observer observer) {
		super("Teacher Main Menu", 10, 10, 900, 1000, observer);
		put(tabbedPane, 0, 0, this.getBounds().width, this.getBounds().height);
		for(Group group : groups) {
			new GroupPanel(group);
		}
		for(Course c : courses) {
			new CoursePanel(c, grades.get(c.getName()));
		}
		new EnrollmentsPanel(enrollments);
	}
}
