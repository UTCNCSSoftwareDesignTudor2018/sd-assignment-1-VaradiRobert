package view.commands;

public class GradeStudentCommand implements Command {
	private String studentName;
	private String courseName;
	private int grade;
	public GradeStudentCommand(String studentName, String courseName, int grade) {
		this.studentName = studentName;
		this.courseName = courseName;
		this.grade = grade;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getGrade() {
		return grade;
	}
}
