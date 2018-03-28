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
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import service.response.Response;
import utilities.Observer;
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
	
	public TeacherMainMenu(Teacher teacher, List<Group> groups, List<Course> courses) {
		this(teacher, groups, courses, null);
	}
	
	public TeacherMainMenu(Teacher teacher, List<Group> groups, List<Course> courses, Observer observer) {
		super("Teacher Main Menu", 10, 10, 900, 1000, observer);
		put(tabbedPane, 0, 0, this.getBounds().width, this.getBounds().height);
		for(Group group : groups) {
			new GroupPanel(group);
		}
	}
	
}
