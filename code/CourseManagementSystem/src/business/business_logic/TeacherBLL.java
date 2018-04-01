package business.business_logic;

import java.util.ArrayList;
import java.util.List;

import business.interfaces.CourseInterface;
import business.interfaces.EnrollmentInterface;
import business.interfaces.GradeInterface;
import business.interfaces.GroupInterface;
import business.interfaces.StudentInterface;
import business.interfaces.TeacherInterface;
import business.security.PasswordEncrypter;
import persistence.dao.TeacherDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import persistence.interfaces.TeacherDAOInterface;

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
		enrollmentBLL.removeStudent(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void acceptStudentEnrollmentRequest(String studentName, String courseName) {
		StudentInterface studentBLL = new StudentBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.acceptEnrollment(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void declineStudentEnrollmentRequest(String studentName, String courseName) {
		StudentInterface studentBLL = new StudentBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		enrollmentBLL.declineEnrollment(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void gradeStudent(String studentName, String courseName, double grade) {
		GradeInterface gradeBLL = new GradeBLL();
		CourseInterface courseBLL = new CourseBLL();
		StudentInterface studentBLL = new StudentBLL();
		Course course = courseBLL.getCourseByName(courseName);
		Student student = studentBLL.getStudentByUserName(studentName);
		Grade g = gradeBLL.getGradeByCourseAndStudentId(course.getId(), student.getIdentityCardNumber());
		System.err.println(g.getId());
		System.err.println("NEW GRADE VALUE = " + grade);
		g.setValue(grade);
		gradeBLL.updateGrade(g);
	}

	@Override
	public List<Student> getStudentsEnrolledTo(String courseName) {
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		return course.getEnrolledStudents();
	}

	@Override
	public List<Course> getTaughtCourses(String userName) {
		Teacher teacher = getTeacherByUserName(userName);
		CourseInterface courseBLL = new CourseBLL();
		return courseBLL.getCoursesByTeacherId(teacher.getId());
	}

	@Override
	public Teacher getTeacherById(int teacherId) {
		return teacherDAO.findById(teacherId);
	}
	@Override
	public Teacher getTeacherByUserName(String teacherName) {
		return teacherDAO.getTeacherByUserName(teacherName);
	}

	@Override
	public List<Group> getAllGroups() {
		GroupInterface groupBLL = new GroupBLL();
		StudentInterface studentBLL = new StudentBLL();
		List<Group> groups = groupBLL.getAll();
		for(Group g : groups) {
			g.setStudents(studentBLL.getStudentsByGroupId(g.getId()));
		}
		return groups;
	}
	
	

	@Override
	public List<Enrollment> getTaughtCoursesEnrollments(String loggedInUserName) {
		Teacher teacher = teacherDAO.getTeacherByUserName(loggedInUserName);
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		CourseInterface courseBLL = new CourseBLL();
		List<Enrollment> allEnrollments = new ArrayList<Enrollment>();
		List<Course> courses = courseBLL.getCoursesByTeacherId(teacher.getId());
		for(Course c : courses) {
			List<Enrollment> enrollmentsForCurrentCourse = enrollmentBLL.getEnrollmentsByCourseId(c.getId());
			for(Enrollment e : enrollmentsForCurrentCourse) {
				e.setCourse(c);
				allEnrollments.add(e);
			}
		}
		return allEnrollments;
	}

	@Override
	public List<Grade> getGrades(String userName) {
		CourseInterface courseBLL = new CourseBLL();
		List<Grade> allGrades = new ArrayList<Grade>();
		Teacher teacher = teacherDAO.getTeacherByUserName(userName);
		List<Course> courses = courseBLL.getCoursesByTeacherId(teacher.getId());
		GradeInterface gradeBLL = new GradeBLL();
		for(Course c : courses) {
			List<Grade> courseGrades = gradeBLL.getGradesByCourseId(c.getId());
			for(Grade g : courseGrades) {
				g.setCourse(c);
				allGrades.add(g);
			}
		}
		return allGrades;
	}
	
}
