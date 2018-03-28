package view.views.factory;

import service.response.LoginResponse;
import service.response.Response;
import service.response.StudentMainMenuResponse;
import service.response.StudentProfileResponse;
import service.response.TeacherMainMenuResponse;
import view.views.LoginView;
import view.views.StudentMainMenu;
import view.views.StudentProfileView;
import view.views.TeacherMainMenu;
import view.views.View;

public class ViewFactory {
	public static View createView(Response response) {
		if(response instanceof LoginResponse) {
			return new LoginView();
		} else if(response instanceof StudentProfileResponse) {
			return new StudentProfileView(((StudentProfileResponse) response).getStudent(), ((StudentProfileResponse) response).getCourses(), ((StudentProfileResponse) response).getEnrollments(), ((StudentProfileResponse) response).getGroup(), ((StudentProfileResponse) response).getGrades(), ((StudentProfileResponse) response).getExams());
		} else if(response instanceof StudentMainMenuResponse) {
			return new StudentMainMenu();
		} else if(response instanceof TeacherMainMenuResponse) {
			return new TeacherMainMenu(((TeacherMainMenuResponse) response).getTeacher(), ((TeacherMainMenuResponse) response).getGroups(), ((TeacherMainMenuResponse) response).getCourses());
		}
		return null;
	}
}
