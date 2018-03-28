package service.interfaces;

import java.util.List;

import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;

public interface TeacherInterface {
	public boolean login(String userName, String password);
	public void removeStudentFromCourse(String studentName, String courseName);
	public void acceptStudentEnrollmentRequest(String studentName, String courseName);
	public void declineStudentEnrollmentRequest(String studentName, String courseName);
	public void gradeStudent(String studentName, String courseName, int grade, String teacherName);
	public List<Student> getStudentsEnrolledTo(String courseName);
	public List<Course> getTaughtCourses(String userName);
	public List<Enrollment> getEnrollmentRequests(String userName, String courseName);
	public Teacher getTeacherById(int teacherId);
}
