package view.commands;

public class SendEnrollmentRequestCommand implements Command {
	private String courseName;
	private String teacherName;
	public SendEnrollmentRequestCommand(String courseName, String teacherName) {
		this.courseName = courseName;
		this.teacherName = teacherName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
}
