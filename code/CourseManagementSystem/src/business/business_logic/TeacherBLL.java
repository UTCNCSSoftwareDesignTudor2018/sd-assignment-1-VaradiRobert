package business.business_logic;

import java.util.List;

import business.security.PasswordEncrypter;
import persistence.dao.TeacherDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
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
	private ExamBLL examBLL;
	
	public TeacherBLL() {
		this.teacherDAO = new TeacherDAO();
		this.pe = new PasswordEncrypter();
		this.studentBLL = new StudentBLL();
		this.enrollmentBLL = new EnrollmentBLL();
		this.courseBLL = new CourseBLL();
		this.examBLL = new ExamBLL();
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
	public void gradeStudent(String studentName, String courseName, int grade, String teacherName) {
		Student student = studentBLL.getStudentByUserName(studentName);
		Course course = courseBLL.getCourseByName(courseName);
		Teacher teacher = getTeacherByName(teacherName);
		gradeBLL.addGrade(student, course, teacher, grade);
	}

	@Override
	public List<Student> getStudentsEnrolledTo(String courseName) {
		Course course = courseBLL.getCourseByName(courseName);
		return course.getEnrolledStudents();
	}

	@Override
	public List<Course> getTaughtCourses(String userName) {
		Teacher teacher = getTeacherByName(userName);
		return examBLL.getCourses(teacher);
	}

	@Override
	public List<Exam> getExams(String userName) {
		Teacher teacher = getTeacherByName(userName);
		return examBLL.getExams(teacher);
	}

	@Override
	public List<Enrollment> getEnrollmentRequests(String userName, String courseName) {
		return null;
	}

	public Teacher getTeacherById(int teacherId) {
		return teacherDAO.getAllObjectsWhere(t -> ((Teacher)t).getId() == teacherId).get(0);
	}
	
	public Teacher getTeacherByName(String teacherName) {
		return teacherDAO.getAllObjectsWhere(t -> ((Teacher)t).getUserName().equals(teacherName)).get(0);
	}
}
