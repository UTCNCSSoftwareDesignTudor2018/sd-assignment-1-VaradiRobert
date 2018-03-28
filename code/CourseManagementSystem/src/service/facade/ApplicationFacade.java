package service.facade;

import service.controllers.StudentController;
import service.controllers.TeacherController;
import service.response.LoginResponse;
import service.response.Response;
import service.response.StudentMainMenuResponse;
import service.response.StudentProfileResponse;
import service.response.TeacherMainMenuResponse;
import utilities.Observer;
import view.commands.CreateProfileCommand;
import view.commands.SendEnrollmentRequestCommand;
import view.commands.StudentLoginCommand;
import view.commands.StudentLogoutCommand;
import view.commands.TeacherLoginCommand;
import view.commands.UnenrollFromCourseCommand;
import view.commands.UpdateProfileCommand;
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
		return new LoginResponse();
	}
	
	public Response execute(UpdateProfileCommand command) {
		studentController.updateProfile(command);
		return null;
	}
	
	public Response execute(ViewProfileCommand command) {
		return studentController.getProfile(command);
	}
	
	public Response execute(StudentLoginCommand command) {
		boolean isLoggedIn = studentController.login(command);
		if(isLoggedIn) {
			return new StudentMainMenuResponse();
		}
		return new LoginResponse();
	}

	public Response execute(TeacherLoginCommand command) {
		boolean isLoggedIn = teacherController.login(command);
		if(isLoggedIn) {
			return new TeacherMainMenuResponse();
		}
		return new LoginResponse();
	}
	
	public Response execute(UnenrollFromCourseCommand command) {
		studentController.unenrollFromCourse(command);
		Response response = new StudentProfileResponse();
		return response;
	}

	public Response execute(SendEnrollmentRequestCommand command) {
		studentController.enrollToCourse(command);
		Response response = new StudentProfileResponse();
		return response;
	}

	public Response execute(StudentLogoutCommand command) {
		studentController.logout();
		return new LoginResponse();
	}
	
}
