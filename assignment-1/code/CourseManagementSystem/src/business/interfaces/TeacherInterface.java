package business.interfaces;

import java.util.List;

import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Grade;
import persistence.entities.Group;
import persistence.entities.Student;
import persistence.entities.Teacher;

public interface TeacherInterface {
	public boolean login(String userName, String password);
	public void removeStudentFromCourse(String studentName, String courseName);
	public void acceptStudentEnrollmentRequest(String studentName, String courseName);
	public void declineStudentEnrollmentRequest(String studentName, String courseName);
	public void gradeStudent(String studentName, String courseName, double grade);
	public List<Student> getStudentsEnrolledTo(String courseName);
	public List<Course> getTaughtCourses(String userName);
	public List<Grade> getGrades(String userName);
	public Teacher getTeacherById(int teacherId);
	public Teacher getTeacherByUserName(String userName);
	public List<Group> getAllGroups();
	public List<Enrollment> getTaughtCoursesEnrollments(String loggedInUserName);
}
