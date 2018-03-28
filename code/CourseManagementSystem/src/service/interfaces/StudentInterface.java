package service.interfaces;

import java.util.List;

import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;

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
}
