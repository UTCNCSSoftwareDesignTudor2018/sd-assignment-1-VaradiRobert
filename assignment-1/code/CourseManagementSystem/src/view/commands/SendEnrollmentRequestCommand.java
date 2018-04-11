package view.commands;

public class SendEnrollmentRequestCommand implements Command {
	private String courseName;
	public SendEnrollmentRequestCommand(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
}
