package business.interfaces;

import java.util.List;

import persistence.domain_model.Enrollment;

public interface EnrollmentDAOInterface {
	public List<Enrollment> findAll();
	public List<Enrollment> findAllByCourseId(int courseId);
	public List<Enrollment> findAllByStudentId(int studentId);
	public void addEnrollment(Enrollment enrollment);
	public void updateEnrollment(Enrollment enrollment);
	public Enrollment findByStudentAndCourseIds(int studentId, int courseId);
}
