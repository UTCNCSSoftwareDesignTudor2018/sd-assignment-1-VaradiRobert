package view.commands;

public class UnenrollFromCourseCommand implements Command {
	private String courseName;
	private String teacherName;
	public UnenrollFromCourseCommand(String courseName, String teacherName) {
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
