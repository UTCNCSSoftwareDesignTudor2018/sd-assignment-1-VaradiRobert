package business.business_logic;

import java.util.List;

import business.interfaces.TeacherDAOInterface;
import business.security.PasswordEncrypter;
import persistence.dao.TeacherDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import service.interfaces.CourseInterface;
import service.interfaces.EnrollmentInterface;
import service.interfaces.StudentInterface;
import service.interfaces.TeacherInterface;

public class TeacherBLL implements TeacherInterface {
	private TeacherDAOInterface teacherDAO;
	
	public TeacherBLL() {
		this.teacherDAO = new TeacherDAO();
	}

	@Override
	public boolean login(String userName, String password) {
		PasswordEncrypter pe = new PasswordEncrypter();
		Teacher teacher = teacherDAO.getTeacherByUserName(userName);	
		return pe.match(teacher.getPassword(), password);
	}

	@Override
	public void removeStudentFromCourse(String studentName, String courseName) {
		StudentInterface studentBLL = new StudentBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.unenrollStudent(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void acceptStudentEnrollmentRequest(String studentName, String courseName) {
		StudentInterface studentBLL = new StudentBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.acceptStudentEnrollmentRequest(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void declineStudentEnrollmentRequest(String studentName, String courseName) {
		StudentInterface studentBLL = new StudentBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.acceptStudentEnrollmentRequest(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void gradeStudent(String studentName, String courseName, int grade, String teacherName) {
		/**Student student = studentBLL.getStudentByUserName(studentName);
		Course course = courseBLL.getCourseByName(courseName);
		Teacher teacher = getTeacherByName(teacherName);
		gradeBLL.addGrade(student, course, teacher, grade);*/
	}

	@Override
	public List<Student> getStudentsEnrolledTo(String courseName) {
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		return course.getEnrolledStudents();
	}

	@Override
	public List<Course> getTaughtCourses(String userName) {
		Teacher teacher = getTeacherByName(userName);
		CourseInterface courseBLL = new CourseBLL();
		return courseBLL.getCoursesByTeacherId(teacher.getId());
	}

	@Override
	public List<Enrollment> getEnrollmentRequests(String userName, String courseName) {
		return null;
	}

	public Teacher getTeacherById(int teacherId) {
		return teacherDAO.findById(teacherId);
	}
	
	public Teacher getTeacherByName(String teacherName) {
		return teacherDAO.getTeacherByUserName(teacherName);
	}
}
