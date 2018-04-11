package view.commands;

public class RemoveStudentFromCourseCommand implements Command {
	private String studentName;
	private String courseName;
	public RemoveStudentFromCourseCommand(String studentName, String courseName) {
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
