package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.response.Response;
import utilities.Observer;
import view.commands.StudentLoginCommand;
import view.commands.TeacherLoginCommand;
import view.views.factory.ViewFactory;

@SuppressWarnings("serial")
public class LoginView extends View {
	private JButton loginButton = new JButton("Login");
	private JButton createAccountButton = new JButton("Create Account");
	private JTextField userNameTextField = new JTextField("rdunrige6");
	private JPasswordField passwordField = new JPasswordField("i2q7mxK9kCxL");
	private JCheckBox teacherLoginCheckBox = new JCheckBox("Login as teacher");
	private JButton exitButton = new JButton("Exit");
	public LoginView() {
		this(null);
	}
	public LoginView(Observer observer) {
		super("Login", 100, 100, 400, 400, observer);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		put(new JLabel("User name"), LABEL_X_POS, 10, LABEL_WIDTH, LABEL_HEIGHT);
		put(new JLabel("Password"), LABEL_X_POS, 40, LABEL_WIDTH, LABEL_HEIGHT);
		put(userNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 10, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(passwordField, LABEL_X_POS + LABEL_WIDTH + 10, 40, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(teacherLoginCheckBox, 90, 90, 200, 25);
		put(loginButton, 90, 120, 100, 25);
		put(createAccountButton, 90, 150, 200, 25);
		put(exitButton, 90, 180, 200, 25);
		setActionListeners();
	}
	
	private void setActionListeners() {		
		setLoginButtonListener();
		setCreateAccountButtonListener();
		setExitButtonListener();
	}
	
	private void setLoginButtonListener() {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Response response = null;
				if(teacherLoginCheckBox.isSelected()) {
					response = getObserver().execute(new TeacherLoginCommand(userNameTextField.getText(), new String(passwordField.getPassword())));
				} else {
					response = getObserver().execute(new StudentLoginCommand(userNameTextField.getText(), new String(passwordField.getPassword())));
				}
				View nextView = ViewFactory.createView(response);
				if(nextView == null) {
					System.err.println("NULL");
				}
				Observer obs = getObserver();
				close();
				nextView.setObserver(obs);
			}
		});
	}
	
	private void setCreateAccountButtonListener() {
		createAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new CreateProfileView(getObserver());
				close();
			}
		});
	}
	
	private void setExitButtonListener() {
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				close();
			}
		});
	}
}
