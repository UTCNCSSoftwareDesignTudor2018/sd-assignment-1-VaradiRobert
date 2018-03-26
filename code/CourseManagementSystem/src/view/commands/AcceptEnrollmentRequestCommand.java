package view.commands;

public class AcceptEnrollmentRequestCommand implements Command {
	private String studentName;
	private String courseName;
	public AcceptEnrollmentRequestCommand(String studentName, String courseName) {
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
