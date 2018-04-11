package business.business_logic;

import java.util.List;

import business.interfaces.CourseInterface;
import business.interfaces.EnrollmentInterface;
import business.interfaces.StudentInterface;
import business.interfaces.TeacherInterface;
import persistence.dao.CourseDAO;
import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Teacher;
import persistence.interfaces.CourseDAOInterface;

public class CourseBLL implements CourseInterface {
	private CourseDAOInterface courseDAO;
	public CourseBLL() {
		this.courseDAO = new CourseDAO();
	}
	
	@Override
	public Course getCourse(int courseId) {
		Course course =  courseDAO.getCourseById(courseId);
		TeacherInterface teacherBLL = new TeacherBLL();
		course.setTeacher(teacherBLL.getTeacherById(course.getTeacherId()));
		return course;
	}

	@Override
	public Course getCourseByName(String courseName) {
		Course course =  courseDAO.getCourseByName(courseName);
		TeacherInterface teacherBLL = new TeacherBLL();
		course.setTeacher(teacherBLL.getTeacherById(course.getTeacherId()));
		return course;
	}

	@Override
	public List<Course> getCoursesByTeacherId(int teacherId) {
		TeacherInterface teacherBLL = new TeacherBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		StudentInterface studentBLL = new StudentBLL();
		Teacher teacher = teacherBLL.getTeacherById(teacherId);
		List<Course> courses = courseDAO.findCoursesByTeacherId(teacherId);
		for(Course c : courses) {
			List<Enrollment> enrollments = enrollmentBLL.getEnrollmentsByCourseId(c.getId());
			c.setEnrolledStudents(studentBLL.getByEnrollments(enrollments));
			c.setTeacher(teacher);
		}
		return courses;
	}

	@Override
	public List<Course> getAll() {
		return courseDAO.findAll();
	}
	
}
