package view.commands;

public class CancelEnrollmentCommand implements Command {
	private String courseName;
	public CancelEnrollmentCommand(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
