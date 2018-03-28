package business.business_logic;

import java.util.List;

import business.interfaces.EnrollmentDAOInterface;
import persistence.dao.EnrollmentDAO;
import persistence.domain_model.Enrollment;
import service.interfaces.CourseInterface;
import service.interfaces.EnrollmentInterface;

public class EnrollmentBLL implements EnrollmentInterface {
	private EnrollmentDAOInterface enrollmentDAO;
	public static final String STATUS_REQUEST = "Request";
	public static final String STATUS_ACCEPTED = "Accepted";
	public static final String STATUS_DECLINED = "Declined";
	public static final String STATUS_DELETED = "Deleted";
	public EnrollmentBLL() {
		this.enrollmentDAO = new EnrollmentDAO();
	}

	@Override
	public List<Enrollment> getEnrollments(int studentId) {
		CourseInterface courseBLL = new CourseBLL();
		List<Enrollment> enrollments = enrollmentDAO.findAllByStudentId(studentId);
		for(Enrollment enrollment : enrollments) {
			enrollment.setCourse(courseBLL.getCourse(enrollment.getCourseId()));
		}
		return enrollments;
	}

	@Override
	public void unenrollStudent(int studentId, int courseId) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(studentId, courseId);
		enrollment.setStatus(STATUS_DELETED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

	@Override
	public void acceptEnrollment(int studentId, int courseId) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(studentId, courseId);
		enrollment.setStatus(STATUS_ACCEPTED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

	@Override
	public void acceptStudentEnrollmentRequest(int studentId, int courseId) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(studentId, courseId);
		enrollment.setStatus(STATUS_DECLINED);
		enrollmentDAO.updateEnrollment(enrollment);
	}
}
