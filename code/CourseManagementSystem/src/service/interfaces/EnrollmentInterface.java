package service.interfaces;

import java.util.List;

import persistence.domain_model.Enrollment;

public interface EnrollmentInterface {
	public List<Enrollment> getEnrollments(int studentId);
	public void unenrollStudent(int studentId, int courseId);
	public void acceptEnrollment(int studentId, int courseId);
	public void acceptStudentEnrollmentRequest(int identityCardNumber, int id);
}
