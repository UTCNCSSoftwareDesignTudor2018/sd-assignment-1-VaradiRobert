package view.commands;

public class TeacherLoginCommand implements Command {
	private String userName;
	private String password;
	public TeacherLoginCommand(String userName, String password) {
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
