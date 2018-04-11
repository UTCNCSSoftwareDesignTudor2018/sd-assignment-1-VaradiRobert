package service.controllers;

import java.util.List;

import business.business_logic.TeacherBLL;
import business.interfaces.TeacherInterface;
import persistence.entities.Enrollment;
import persistence.entities.Grade;
import persistence.entities.Group;
import persistence.entities.Teacher;
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
		loggedInUserName = command.getUserName();
		return loggedIn;
	}
	
	public void removeStudentFromCourse(RemoveStudentFromCourseCommand command) {
		teacherBLL.removeStudentFromCourse(command.getStudentName(), command.getCourseName());
	}
	
	public Response acceptEnrollmentRequest(AcceptEnrollmentRequestCommand command) {
		teacherBLL.acceptStudentEnrollmentRequest(command.getStudentName(), command.getCourseName());
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Enrollment> enrollments = teacherBLL.getTaughtCoursesEnrollments(loggedInUserName);
		List<Grade> grades = teacherBLL.getGrades(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setEnrollments(enrollments);
		response.setGrades(grades);
		return response;
	}
	
	public Response declineEnrollmentRequest(DeclineEnrollmentRequestCommand command) {
		teacherBLL.declineStudentEnrollmentRequest(command.getStudentName(), command.getCourseName());
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Enrollment> enrollments = teacherBLL.getTaughtCoursesEnrollments(loggedInUserName);
		List<Grade> grades = teacherBLL.getGrades(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setEnrollments(enrollments);
		response.setGrades(grades);
		return response;
	}
	
	public Response gradeStudent(GradeStudentCommand command) {
		teacherBLL.gradeStudent(command.getStudentName(), command.getCourseName(), command.getGrade());
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Enrollment> enrollments = teacherBLL.getTaughtCoursesEnrollments(loggedInUserName);
		List<Grade> grades = teacherBLL.getGrades(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setEnrollments(enrollments);
		response.setGrades(grades);
		return response;
	}

	public Response getProfile(TeacherLoginCommand command) {
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Enrollment> enrollments = teacherBLL.getTaughtCoursesEnrollments(loggedInUserName);
		List<Grade> grades = teacherBLL.getGrades(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setEnrollments(enrollments);
		response.setGrades(grades);
		return response;
	}

	public void logout() {
		loggedInUserName = "";
	}

	public Response deleteStudentFromCourse(RemoveStudentFromCourseCommand command) {
		teacherBLL.removeStudentFromCourse(command.getStudentName(), command.getCourseName());
		Teacher teacher = teacherBLL.getTeacherByUserName(loggedInUserName);
		List<Group> groups = teacherBLL.getAllGroups();
		List<Enrollment> enrollments = teacherBLL.getTaughtCoursesEnrollments(loggedInUserName);
		List<Grade> grades = teacherBLL.getGrades(loggedInUserName);
		TeacherMainMenuResponse response = new TeacherMainMenuResponse();
		response.setTeacher(teacher);
		response.setGroups(groups);
		response.setEnrollments(enrollments);
		response.setGrades(grades);
		return response;
	}
}
