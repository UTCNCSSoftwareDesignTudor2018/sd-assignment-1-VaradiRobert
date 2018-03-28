package business.interfaces;

import java.util.List;

import persistence.domain_model.Course;

public interface CourseDAOInterface {
	public List<Course> findAll();
	public Course getCourseById(int courseId);
	public List<Course> getCoursesByIds(List<Integer> ids);
	public Course getCourseByName(String courseName);
	public List<Course> findCoursesByTeacherId(int teacherId);
}
