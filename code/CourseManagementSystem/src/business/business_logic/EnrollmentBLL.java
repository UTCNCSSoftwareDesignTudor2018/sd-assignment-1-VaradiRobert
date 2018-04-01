package business.business_logic;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import business.interfaces.CourseInterface;
import business.interfaces.EnrollmentInterface;
import business.interfaces.GradeInterface;
import business.interfaces.StudentInterface;
import persistence.dao.EnrollmentDAO;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Grade;
import persistence.interfaces.EnrollmentDAOInterface;

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
		enrollment.setStatus(STATUS_UNENROLLED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

	@Override
	public void acceptEnrollment(int studentId, int courseId) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(studentId, courseId);
		enrollment.setStatus(STATUS_ACCEPTED);
		enrollmentDAO.updateEnrollment(enrollment);
		GradeInterface gradeBLL = new GradeBLL();
		Grade grade = new Grade();
		grade.setCourseId(courseId);
		grade.setStudentId(studentId);
		grade.setValue(-1);
		grade.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		gradeBLL.saveGrade(grade);
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
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(identityCardNumber, courseId);
		enrollment.setStatus(STATUS_REQUEST);
		enrollmentDAO.updateEnrollment(enrollment);
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

	@Override
	public void cancelEnrollment(int identityCardNumber, int id) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(identityCardNumber, id);
		enrollment.setStatus(STATUS_CANCELLED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

	@Override
	public void removeStudent(int identityCardNumber, int id) {
		Enrollment enrollment = enrollmentDAO.findByStudentAndCourseIds(identityCardNumber, id);
		enrollment.setStatus(STATUS_DELETED);
		enrollmentDAO.updateEnrollment(enrollment);
	}

}
