package view.commands;

public class DeclineEnrollmentRequestCommand implements Command {
	private String studentName;
	private String courseName;
	public DeclineEnrollmentRequestCommand(String studentName, String courseName) {
		this.studentName = studentName;
		this.courseName = courseName;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getCourseName() {
		return courseName;
	}
}
