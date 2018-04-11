package view.commands;

public class UnenrollFromCourseCommand implements Command {
	private String courseName;
	public UnenrollFromCourseCommand(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseName() {
		return courseName;
	}
}
