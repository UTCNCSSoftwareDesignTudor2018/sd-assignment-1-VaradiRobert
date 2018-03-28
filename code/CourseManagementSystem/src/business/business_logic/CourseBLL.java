package business.business_logic;

import java.util.List;

import business.interfaces.CourseDAOInterface;
import persistence.dao.CourseDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Teacher;
import service.interfaces.CourseInterface;
import service.interfaces.TeacherInterface;

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
		Teacher teacher = teacherBLL.getTeacherById(teacherId);
		List<Course> courses = courseDAO.findCoursesByTeacherId(teacherId);
		for(Course c : courses) {
			c.setTeacher(teacher);
		}
		return courses;
	}
	
}
