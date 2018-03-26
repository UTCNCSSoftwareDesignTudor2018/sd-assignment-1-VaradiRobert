package business.business_logic;

import java.util.List;

import persistence.dao.CourseDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Student;

public class CourseBLL {
	private CourseDAO courseDAO;
	//private EnrollmentBLL enrollmentBLL;
	public CourseBLL() {
		this.courseDAO = new CourseDAO();
		///this.enrollmentBLL = new EnrollmentBLL();
	}
	
	public Course getCourseByName(String courseName) {
		Course course = courseDAO.getAllObjectsWhere(c -> ((Course)c).getName().equals(courseName)).get(0);
		//List<Student> students = enrollmentBLL.getEnrolledStudents(course.getId());
		//course.setEnrolledStudents(students);
		return null;
	}
	
	public Course getCourseById(int courseId) {
		List<Course> courses = courseDAO.getAllObjectsWhere(c -> ((Course)c).getId() == courseId);
		return courses.get(0);
	}

}
