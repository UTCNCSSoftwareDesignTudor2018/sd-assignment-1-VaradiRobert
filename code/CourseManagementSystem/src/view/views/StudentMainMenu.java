package view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import service.response.Response;
import utilities.Observer;
import view.commands.StudentLogoutCommand;
import view.commands.ViewProfileCommand;
import view.views.factory.ViewFactory;

@SuppressWarnings("serial")
public class StudentMainMenu extends View {
	public StudentMainMenu(Observer observer) {
		super("Main Menu", 10, 10, 500, 500, observer);
	}
	
	private JButton viewProfileButton = new JButton("View Profile");
	private JButton logoutButton = new JButton("Logout");
	
	public StudentMainMenu() {
		this(null);
		put(viewProfileButton, 10, 10, 200, 25);
		put(logoutButton, 10, 40, 200, 25);
		setActionListeners();
	}
	
	public void setActionListeners() {
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
		viewProfileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Response response = getObserver().execute(new ViewProfileCommand());
				View nextView = ViewFactory.createView(response);
				Observer obs = getObserver();
				close();
				nextView.setObserver(obs);
			}
		});
	}
}
