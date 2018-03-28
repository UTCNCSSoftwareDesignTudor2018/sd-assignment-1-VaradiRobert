package service.controllers;

import business.business_logic.TeacherBLL;
import service.interfaces.TeacherInterface;
import view.commands.AcceptEnrollmentRequestCommand;
import view.commands.DeclineEnrollmentRequestCommand;
import view.commands.GradeStudentCommand;
import view.commands.RemoveStudentFromCourseCommand;
import view.commands.TeacherLoginCommand;

public class TeacherController {
	private TeacherInterface teacherBLL;
	private String loggedInUserName;
	
	public TeacherController() {
		teacherBLL = new TeacherBLL();
	}
	
	public boolean login(TeacherLoginCommand command) {
		boolean loggedIn = teacherBLL.login(command.getUserName(), command.getPassword());
		loggedInUserName = command.getUserName();
		return loggedIn;
	}
	
	public void removeStudentFromCourse(RemoveStudentFromCourseCommand command) {
		teacherBLL.removeStudentFromCourse(command.getStudentName(), command.getCourseName());
	}
	
	public void acceptEnrollmentRequest(AcceptEnrollmentRequestCommand command) {
		teacherBLL.acceptStudentEnrollmentRequest(command.getStudentName(), command.getCourseName());
	}
	
	public void declineEnrollmentRequest(DeclineEnrollmentRequestCommand command) {
		teacherBLL.declineStudentEnrollmentRequest(command.getStudentName(), command.getCourseName());
	}
	
	public void gradeStudent(GradeStudentCommand command) {
		teacherBLL.gradeStudent(command.getStudentName(), command.getCourseName(), command.getGrade(), loggedInUserName);
	}
}
