package view.views;

import utilities.Observer;

@SuppressWarnings("serial")
public class TeacherMainMenu extends View {

	public TeacherMainMenu() {
		this(null);
	}
	
	
	
	public TeacherMainMenu(Observer observer) {
		super("Teacher Main Menu", 10, 10, 400, 300, observer);
	}
	
}
