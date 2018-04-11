package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.response.Response;
import utilities.Observer;
import view.commands.CreateProfileCommand;
import view.views.factory.ViewFactory;

@SuppressWarnings("serial")
public class CreateProfileView extends View {
	private JTextField userNameTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JPasswordField passwordAgainField = new JPasswordField();
	private JTextField emailTextField = new JTextField();
	private JTextField firstNameTextField = new JTextField();
	private JTextField lastNameTextField = new JTextField();
	private JTextField phoneTextField = new JTextField();
	private JTextField addressTextField = new JTextField();
	private JButton createAccountButton = new JButton("Create Account");
	private JButton backButton = new JButton("Back");
	public CreateProfileView(Observer observer) {
		super("Create Account", 100, 100, 300, 500, observer);
		put(new JLabel("User Name"), LABEL_X_POS, 10, LABEL_WIDTH, LABEL_HEIGHT);
		put(userNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 10, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Password"), LABEL_X_POS, 40, LABEL_WIDTH, LABEL_HEIGHT);
		put(passwordField, LABEL_X_POS + LABEL_WIDTH + 10, 40, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Confirm Password"), LABEL_X_POS, 70, LABEL_WIDTH, LABEL_HEIGHT);
		put(passwordAgainField, LABEL_X_POS + LABEL_WIDTH + 10, 70, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Email"), LABEL_X_POS, 100, LABEL_WIDTH, LABEL_HEIGHT);
		put(emailTextField, LABEL_X_POS + LABEL_WIDTH + 10, 100, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("First Name"), LABEL_X_POS, 130, LABEL_WIDTH, LABEL_HEIGHT);
		put(firstNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 130, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Last Name"), LABEL_X_POS, 160, LABEL_WIDTH, LABEL_HEIGHT);
		put(lastNameTextField, LABEL_X_POS + LABEL_WIDTH + 10, 160, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Phone number"), LABEL_X_POS, 190, LABEL_WIDTH, LABEL_HEIGHT);
		put(phoneTextField, LABEL_X_POS + LABEL_WIDTH + 10, 190, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(new JLabel("Address"), LABEL_X_POS, 220, LABEL_WIDTH, LABEL_HEIGHT);
		put(addressTextField, LABEL_X_POS + LABEL_WIDTH + 10, 220, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		put(createAccountButton, 70, 250, 200, 25);
		put(backButton, 70, 280, 200, 25);
		setActionListeners();
	}
	
	private void setActionListeners() {
		setCreateAccountButtonListener();
		setBackButtonListener();
	}
	
	private void setCreateAccountButtonListener() {
		createAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Response response = getObserver().execute(new CreateProfileCommand(userNameTextField.getText(), 
						new String(passwordField.getPassword()), 
						new String(passwordAgainField.getPassword()),
						emailTextField.getText(), 
						firstNameTextField.getText(), 
						lastNameTextField.getText(), 
						phoneTextField.getText(), 
						addressTextField.getText()));
				View nextView = ViewFactory.createView(response);
				nextView.setObserver(getObserver());
				close();
			}
		});
	}
	
	private void setBackButtonListener() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new LoginView(getObserver());
				close();
			}
		});
	}
	
}
