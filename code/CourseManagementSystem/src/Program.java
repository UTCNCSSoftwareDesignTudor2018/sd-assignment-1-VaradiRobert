import java.sql.SQLException;
import java.util.List;

import business.business_logic.TeacherBLL;
import persistence.domain_model.Course;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import service.facade.ApplicationFacade;
import view.views.LoginView;

public class Program {
	public static void main(String[] args) throws SQLException {
		Teacher teacher = (new TeacherBLL()).getTeacherById(5);
		List<Course> courses = (new TeacherBLL()).getTaughtCourses(teacher.getUserName());
		for(Course c : courses) {
			List<Student> students = c.getEnrolledStudents();
			for(Student s: students) {
				System.err.println(s.getFirstName() + " " + s.getLastName());
			}
		}
		new LoginView(new ApplicationFacade());
	}
}
