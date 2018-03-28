package business.business_logic;

import java.util.List;

import business.interfaces.EnrollmentDAOInterface;
import persistence.dao.EnrollmentDAO;
import persistence.domain_model.Enrollment;
import service.interfaces.CourseInterface;
import service.interfaces.EnrollmentInterface;
import service.interfaces.StudentInterface;

public class EnrollmentBLL implements EnrollmentInterface {
	private EnrollmentDAOInterface enrollmentDAO;
	public static final String STATUS_REQUEST = "Request";
	public static final String STATUS_ACCEPTED = "Accepted";
	public static final String STATUS_DECLINED = "Declined";
	public static final String STATUS_DELETED = "Deleted";
	public static final String STATUS_UNENROLLED = "Unenrolled";
	public static final String STATUS_CANCELLED = "Cancelled";
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
	public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
		StudentInterface studentBLL = new StudentBLL();
		List<Enrollment> enrollments = enrollmentDAO.findAllByCourseId(courseId);
		for(Enrollment enrollment : enrollments) {
			enrollment.setStudent(studentBLL.getStudentById(enrollment.getStudentId()));
		}
		return enrollments;
	}

	@Override
	public void sendEnrollmentRequest(int identityCardNumber, int courseId) {
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseId(courseId);
		enrollment.setStudentId(identityCardNumber);
		enrollment.setStatus(STATUS_REQUEST);
		enrollment.setId(enrollmentDAO.findAll().size() + 1);
		enrollmentDAO.addEnrollment(enrollment);
	}

	@Override
	public void declineEnrollment(int identityCardNumber, int id) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(identityCardNumber, id);
		enrollment.setStatus(STATUS_DECLINED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

	@Override
	public void saveEnrollment(Enrollment enrollment) {
		enrollment.setId(enrollmentDAO.findAll().size() + 1);
		enrollmentDAO.addEnrollment(enrollment);
	}

}
