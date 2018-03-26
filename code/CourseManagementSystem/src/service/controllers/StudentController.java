package service.controllers;

import java.util.List;

import business.business_logic.StudentBLL;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import view.commands.CreateProfileCommand;
import view.commands.GetCoursesCommand;
import view.commands.GetEnrollmentsCommand;
import view.commands.GetExamsCommand;
import view.commands.GetGradesCommand;
import view.commands.SendEnrollmentRequestCommand;
import view.commands.StudentLoginCommand;
import view.commands.UnenrollFromCourseCommand;
import view.commands.UpdateProfileCommand;
import view.commands.ViewGroupCommand;
import view.commands.ViewProfileCommand;

public class StudentController {
	private StudentBLL studentBLL;
	private String loggedInUserName;
	
	public StudentController() {
		this.studentBLL = new StudentBLL();
		this.loggedInUserName = null;
	}
	
	public boolean login(StudentLoginCommand command) {
		return studentBLL.login(command.getUserName(), command.getPassword());
	}
	
	public void createProfile(CreateProfileCommand command) {
		studentBLL.createProfile(command.getUserName(), command.getPassword(), command.getPasswordAgain(), command.getEmail(), command.getFirstName(), command.getLastName(), command.getPhone(), command.getAddress());
	}
	
	public void updateProfile(UpdateProfileCommand command) {
		studentBLL.updateProfile(loggedInUserName, command.getPassword(), command.getPasswordAgain(), command.getEmail(), command.getFirstName(), command.getLastName(), command.getPhone(), command.getAddress());
	}
	
	public Student viewProfile(ViewProfileCommand command) {
		return studentBLL.viewProfile(loggedInUserName);
	}
	
	public void sendEnrollmentRequest(SendEnrollmentRequestCommand command) {
		studentBLL.sendEnrollmentRequest(loggedInUserName, command.getCourseName(), command.getTeacherName());
	}
	
	public void unenrollFromCourse(UnenrollFromCourseCommand command) {
		studentBLL.unenrollFromCourse(loggedInUserName, command.getCourseName(), command.getTeacherName());
	}
	
	public Group viewGroup(ViewGroupCommand command) {
		return studentBLL.viewGroup(loggedInUserName);
	}
	
	public List<Exam> getExams(GetExamsCommand command) {
		return studentBLL.getExams(loggedInUserName);
	}
	
	public List<Course> getCourses(GetCoursesCommand command) {
		return studentBLL.getCourses(loggedInUserName);
	}
	
	public List<Grade> getGrades(GetGradesCommand command) {
		return studentBLL.getGrades(loggedInUserName);
	}
	
	public List<Enrollment> getEnrollments(GetEnrollmentsCommand command) {
		return studentBLL.getEnrollments(loggedInUserName);
	}
}
