package business.business_logic;

import java.util.List;

import persistence.dao.EnrollmentDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Student;

public class EnrollmentBLL {
	private CourseBLL courseBLL;
	private EnrollmentDAO enrollmentDAO;
	private static int recordCount;
	private static final String STATUS_REQUEST = "Request";
	private static final String STATUS_ACCEPTED = "Accepted";
	private static final String STATUS_DECLINED = "Declined";
	private static final String STATUS_DELETED = "Deleted";
	public EnrollmentBLL() {
		this.courseBLL = new CourseBLL();
		this.enrollmentDAO = new EnrollmentDAO();
		recordCount = enrollmentDAO.getAllObjects().size();
	}
	
	public void enrollStudent(Student student, String courseName) {
		Course course = courseBLL.getCourseByName(courseName);
		Enrollment enrollment = new Enrollment();
		enrollment.setStudentId(student.getIdentityCardNumber());
		enrollment.setCourseId(course.getId());
		enrollment.setId(recordCount + 1);
		enrollment.setStatus(STATUS_REQUEST);
		enrollmentDAO.storeObject(enrollment);
		recordCount++;
	}
	
	public void unenrollStudent(Student student, String courseName) {
		Course course = courseBLL.getCourseByName(courseName);
		Enrollment enrollment = enrollmentDAO.getAllObjectsWhere(e -> ((Enrollment)e).getCourseId() == course.getId() && ((Enrollment)e).getStudentId() == student.getIdentityCardNumber()).get(0);
		enrollment.setStatus(STATUS_DELETED);
		enrollmentDAO.updateObject(enrollment);
	}
	
	public void declineEnrollment(Student student, String courseName) {
		Course course = courseBLL.getCourseByName(courseName);
		Enrollment enrollment = enrollmentDAO.getAllObjectsWhere(e -> ((Enrollment)e).getCourseId() == course.getId()).get(0);
		enrollment.setStatus(STATUS_DECLINED);
		enrollmentDAO.updateObject(enrollment);
	}
	
	public void acceptEnrollment(Student student, String courseName) {
		Course course = courseBLL.getCourseByName(courseName);
		Enrollment enrollment = enrollmentDAO.getAllObjectsWhere(e -> ((Enrollment)e).getCourseId() == course.getId()).get(0);
		enrollment.setStatus(STATUS_ACCEPTED);
		enrollmentDAO.updateObject(enrollment);
	}
	
	public List<Enrollment> getEnrollmentsForStudent(Student student) {
		List<Enrollment> enrollments = enrollmentDAO.getAllObjectsWhere(e -> ((Enrollment)e).getCourseId() == student.getIdentityCardNumber());
		for(Enrollment e : enrollments) {
			e.setCourse(courseBLL.getCourseById(e.getCourseId()));
			e.setStudent(student);
		}
		return enrollments;
	}
}
