package business.interfaces;

import java.util.List;

import persistence.entities.Enrollment;

public interface EnrollmentInterface {
	public List<Enrollment> getEnrollments(int studentId);
	public void unenrollStudent(int studentId, int courseId);
	public void acceptEnrollment(int studentId, int courseId);
	public List<Enrollment> getEnrollmentsByCourseId(int courseId);
	public void sendEnrollmentRequest(int identityCardNumber, int id);
	public void declineEnrollment(int identityCardNumber, int id);
	public void saveEnrollment(Enrollment enrollment);
	public void cancelEnrollment(int identityCardNumber, int id);
	public void removeStudent(int identityCardNumber, int id);
}
