package service.controllers;

import java.util.List;

import business.business_logic.StudentBLL;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import service.response.Response;
import service.response.StudentProfileResponse;
import view.commands.CreateProfileCommand;
import view.commands.SendEnrollmentRequestCommand;
import view.commands.StudentLoginCommand;
import view.commands.UnenrollFromCourseCommand;
import view.commands.UpdateProfileCommand;
import view.commands.ViewProfileCommand;

public class StudentController {
	private StudentBLL studentBLL;
	private String loggedInUserName;
	
	public StudentController() {
		this.studentBLL = new StudentBLL();
		this.loggedInUserName = null;
	}
	
	public boolean login(StudentLoginCommand command) {
		boolean loggedIn = studentBLL.login(command.getUserName(), command.getPassword());
		System.err.println(loggedIn);
		if(loggedIn == true) {
			loggedInUserName = command.getUserName();
		}
		return loggedIn;
	}
	
	public void createProfile(CreateProfileCommand command) {
		studentBLL.createProfile(command.getUserName(), command.getPassword(), command.getPasswordAgain(), command.getEmail(), command.getFirstName(), command.getLastName(), command.getPhone(), command.getAddress());
	}
	
	public void updateProfile(UpdateProfileCommand command) {
		studentBLL.updateProfile(loggedInUserName, command.getPassword(), command.getPasswordAgain(), command.getEmail(), command.getFirstName(), command.getLastName(), command.getPhone(), command.getAddress());
	}
	
	public Response getProfile(ViewProfileCommand command) {
		Student student = studentBLL.getStudentByUserName(loggedInUserName);
		List<Enrollment> enrollments = studentBLL.getEnrollments(loggedInUserName);
		List<Course> courses = studentBLL.getCourses(loggedInUserName);
		Group group = studentBLL.getGroup(loggedInUserName);
		StudentProfileResponse response = new StudentProfileResponse();
		response.setStudent(student);
		response.setEnrollments(enrollments);
		response.setCourses(courses);
		response.setGroup(group);
		return response;
	}
	
	public Response unenrollFromCourse(UnenrollFromCourseCommand command) {
		studentBLL.unenrollFromCourse(loggedInUserName, command.getCourseName());
		Student student = studentBLL.getStudentByUserName(loggedInUserName);
		List<Enrollment> enrollments = studentBLL.getEnrollments(loggedInUserName);
		List<Course> courses = studentBLL.getCourses(loggedInUserName);
		Group group = studentBLL.getGroup(loggedInUserName);
		StudentProfileResponse response = new StudentProfileResponse();
		response.setStudent(student);
		response.setEnrollments(enrollments);
		response.setCourses(courses);
		response.setGroup(group);
		return response;
	}

	public Response enrollToCourse(SendEnrollmentRequestCommand command) {
		studentBLL.sendEnrollmentRequest(loggedInUserName, command.getCourseName());
		Student student = studentBLL.getStudentByUserName(loggedInUserName);
		List<Enrollment> enrollments = studentBLL.getEnrollments(loggedInUserName);
		List<Course> courses = studentBLL.getCourses(loggedInUserName);
		Group group = studentBLL.getGroup(loggedInUserName);
		StudentProfileResponse response = new StudentProfileResponse();
		response.setStudent(student);
		response.setEnrollments(enrollments);
		response.setCourses(courses);
		response.setGroup(group);
		return response;
	}

	public void logout() {
		loggedInUserName = "";
	}
}
