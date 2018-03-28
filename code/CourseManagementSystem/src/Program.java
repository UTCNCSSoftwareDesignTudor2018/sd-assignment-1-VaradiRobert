import java.sql.SQLException;

import service.facade.ApplicationFacade;
import view.views.LoginView;

public class Program {
	public static void main(String[] args) throws SQLException {
		/**StudentInterface studentBLL = new StudentBLL();
		CourseInterface courseBLL = new CourseBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		Student student = studentBLL.getStudentById(61);
		Course course = courseBLL.getCourse(5);
		studentBLL.sendEnrollmentRequest(student.getUserName(), course.getName());
		Enrollment enrollment = (new EnrollmentDAO()).findByStudentAndCourseIds(student.getIdentityCardNumber(), course.getId());
		enrollmentBLL.acceptEnrollment(student.getIdentityCardNumber(), course.getId());
		enrollment = (new EnrollmentDAO()).findByStudentAndCourseIds(student.getIdentityCardNumber(), course.getId());
		System.err.println(enrollment.getStatus());
		enrollmentBLL.declineEnrollment(student.getIdentityCardNumber(), course.getId());
		enrollment = (new EnrollmentDAO()).findByStudentAndCourseIds(student.getIdentityCardNumber(), course.getId());
		System.err.println(enrollment.getStatus());
		enrollmentBLL.unenrollStudent(student.getIdentityCardNumber(), course.getId());
		enrollment = (new EnrollmentDAO()).findByStudentAndCourseIds(student.getIdentityCardNumber(), course.getId());
		System.err.println(enrollment.getStatus());*/	
		new LoginView(new ApplicationFacade());
	}
}
