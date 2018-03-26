package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.facade.ApplicationFacade;
import utilities.Observer;
import view.commands.StudentLoginCommand;

@SuppressWarnings("serial")
public class LoginView extends View {
	private JButton loginButton;
	private JTextField userNameTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	public LoginView(Observer observer) {
		super("Login", 100, 100, 500, 200, observer);
		put(new JLabel("User name"), 10, 10, 100, 25);
		put(new JLabel("Password"), 10, 40, 100, 25);
		put(userNameTextField, 60, 10, 200, 25);
		put(passwordField, 60, 40, 200, 25);
		put(new JCheckBox("Login as teacher"), 90, 90, 100, 25);
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				((ApplicationFacade)(getSubject().getObserver())).execute(new StudentLoginCommand(userNameTextField.getText(), passwordField.getPassword().toString()));
			}
		});
		put(loginButton, 90, 120, 100, 25);
	}
}
