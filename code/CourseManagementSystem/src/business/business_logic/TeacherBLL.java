package business.business_logic;

import java.util.List;

import business_logic.security.PasswordEncrypter;
import persistence.dao.TeacherDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Exam;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import service.interfaces.TeacherInterface;

public class TeacherBLL implements TeacherInterface {
	private TeacherDAO teacherDAO;
	private PasswordEncrypter pe;
	private StudentBLL studentBLL;
	private EnrollmentBLL enrollmentBLL;
	private CourseBLL courseBLL;
	private GradeBLL gradeBLL;
	
	public TeacherBLL() {
		this.teacherDAO = new TeacherDAO();
		this.pe = new PasswordEncrypter();
		this.studentBLL = new StudentBLL();
		this.enrollmentBLL = new EnrollmentBLL();
		this.courseBLL = new CourseBLL();
	}

	@Override
	public boolean login(String userName, String password) {
		List<Teacher> teachers = teacherDAO.getAllObjectsWhere(t -> ((Teacher)t).getUserName().equals(userName) && pe.match(password, ((Teacher)t).getPassword()));
		return teachers.size() > 0;
	}

	@Override
	public void removeStudentFromCourse(String studentName, String courseName) {
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.unenrollStudent(student, courseName);
	}

	@Override
	public void acceptStudentEnrollmentRequest(String studentName, String courseName) {
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.acceptEnrollment(student, courseName);
	}

	@Override
	public void declineStudentEnrollmentRequest(String studentName, String courseName) {
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.declineEnrollment(student, courseName);	
	}

	@Override
	public void gradeStudent(String studentName, String courseName, int grade) {
		Student student = studentBLL.getStudentByUserName(studentName);
		Course course = courseBLL.getCourseByName(courseName);
		
	}

	@Override
	public List<Student> getStudentsEnrolledTo(String courseName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getTaughtCourses(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Exam> getExams(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getEnrollmentRequests(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Teacher getTeacherById(int teacherId) {
		// TODO Auto-generated method stub
		return null;
	}
}
