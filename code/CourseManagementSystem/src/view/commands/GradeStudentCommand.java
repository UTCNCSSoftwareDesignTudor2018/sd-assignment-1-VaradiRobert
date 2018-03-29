package view.commands;

public class GradeStudentCommand implements Command {
	private String studentName;
	private String courseName;
	private double grade;
	public GradeStudentCommand(String studentName, String courseName, double grade) {
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
	public double getGrade() {
		return grade;
	}
}
