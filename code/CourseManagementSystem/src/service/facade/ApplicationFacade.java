package service.facade;

import java.util.List;

import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import service.controllers.StudentController;
import service.controllers.TeacherController;
import service.response.Response;
import utilities.Observer;
import view.commands.AcceptEnrollmentRequestCommand;
import view.commands.Command;
import view.commands.CreateProfileCommand;
import view.commands.DeclineEnrollmentRequestCommand;
import view.commands.GetCoursesCommand;
import view.commands.GetEnrollmentsCommand;
import view.commands.GetExamsCommand;
import view.commands.GetGradesCommand;
import view.commands.GradeStudentCommand;
import view.commands.RemoveStudentFromCourseCommand;
import view.commands.SendEnrollmentRequestCommand;
import view.commands.StudentLoginCommand;
import view.commands.TeacherLoginCommand;
import view.commands.UnenrollFromCourseCommand;
import view.commands.UpdateProfileCommand;
import view.commands.ViewGroupCommand;
import view.commands.ViewProfileCommand;

public class ApplicationFacade implements Observer {
	private StudentController studentController;
	private TeacherController teacherController;
	
	public ApplicationFacade() {
		this.studentController = new StudentController();
		this.teacherController = new TeacherController();
	}
	
	public Response execute(CreateProfileCommand command) {
		studentController.createProfile(command);
		return null;
	}
	
	public Response execute(UpdateProfileCommand command) {
		studentController.updateProfile(command);
		return null;
	}
	
	public Response execute(ViewProfileCommand command) {
		Student student = studentController.viewProfile(command);
		return null;
	}
	
	
	public Response execute(SendEnrollmentRequestCommand command) {
		studentController.sendEnrollmentRequest(command);
		return null;
	}
	
	public Response execute(UnenrollFromCourseCommand command) {
		studentController.unenrollFromCourse(command);
		return null;
	}
	
	public Response execute(ViewGroupCommand command) {
		Group group = studentController.viewGroup(command);
		return null;
	}
	
	public Response execute(StudentLoginCommand command) {
		Boolean loggedIn = studentController.login(command);
		System.err.println(loggedIn);
		return null;
	}
	
	public Response execute(GetExamsCommand command) {
		List<Exam> exams = studentController.getExams(command);
		return null;
	}
	
	public Response execute(GetCoursesCommand command) {
		List<Course> courses = studentController.getCourses(command);
		return null;
	}
	
	public Response execute(GetGradesCommand command) {
		List<Grade> grades = studentController.getGrades(command);
		return null;
	}
	
	public Response execute(GetEnrollmentsCommand command) {
		List<Enrollment> enrollments = studentController.getEnrollments(command);
		return null;
	}
	
	public Response execute(TeacherLoginCommand command) {
		boolean loggedIn = teacherController.login(command);
		return null;
	}
	
	public Response execute(RemoveStudentFromCourseCommand command) {
		teacherController.removeStudentFromCourse(command);
		return null;
	}
	
	public Response execute(AcceptEnrollmentRequestCommand command) {
		teacherController.acceptEnrollmentRequest(command);
		return null;
	}
	
	public Response execute(DeclineEnrollmentRequestCommand command) {
		teacherController.declineEnrollmentRequest(command);
		return null;
	}
	
	public Response execute(GradeStudentCommand command) {
		teacherController.gradeStudent(command);
		return null;
	}
}
