package service.controllers;

import java.util.List;

import business.business_logic.TeacherBLL;
import persistence.domain_model.Course;
import persistence.domain_model.Group;
import persistence.domain_model.Teacher;
import service.interfaces.TeacherInterface;
import service.response.Response;
import service.response.TeacherMainMenuResponse;
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
		System.err.println("TEACHER LOG IN");
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

	public Response getProfile(TeacherLoginCommand command) {
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Course> courses = teacherBLL.getTaughtCourses(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setCourses(courses);
		return response;
	}

	public void logout() {
		loggedInUserName = "";
	}
}
