package view.commands;

public class StudentLoginCommand implements Command {
	private String userName;
	private String password;
	public StudentLoginCommand(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
}
