package business.interfaces;

import java.util.List;

import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Exam;
import persistence.entities.Grade;
import persistence.entities.Group;
import persistence.entities.Student;

public interface StudentInterface {
	public void createProfile(String userName, String password, String passwordAgain, String email, String firstName, String lastName, String phone, String address);
	public void updateProfile(String userName, String password, String passwordAgain, String email, String firstName, String lastName, String phone, String address);
	public Student getProfile(String userName);
	public void sendEnrollmentRequest(String userName, String courseName);
	public void unenrollFromCourse(String userName, String courseName);
	public Group getGroup(String userName);
	public boolean login(String userName, String password);
	public List<Exam> getExams(String userName);
	public List<Course> getCourses(String userName);
	public List<Grade> getGrades(String userName);
	public List<Enrollment> getEnrollments(String userName);
	public Student getStudentByUserName(String studentName);
	public List<Student> getStudentsByGroupId(int groupId);
	public Student getStudentById(int studentId);
	public List<Student> getByEnrollments(List<Enrollment> enrollments);
	public void cancelEnrollmentRequest(String loggedInUserName, String courseName);
}
