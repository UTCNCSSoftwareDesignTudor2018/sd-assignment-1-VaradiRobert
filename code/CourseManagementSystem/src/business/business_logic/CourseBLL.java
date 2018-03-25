package business.business_logic;

import java.util.List;

import persistence.dao.CourseDAO;
import persistence.domain_model.Course;

public class CourseBLL {
	private CourseDAO courseDAO;
	public CourseBLL() {
		this.courseDAO = new CourseDAO();
	}
	
	public Course getCourseByName(String courseName) {
		List<Course> courses = courseDAO.getAllObjectsWhere(c -> ((Course)c).getName().equals(courseName));
		return courses.get(0);
	}
	
	public Course getCourseById(int courseId) {
		List<Course> courses = courseDAO.getAllObjectsWhere(c -> ((Course)c).getId() == courseId);
		return courses.get(0);
	}
}
