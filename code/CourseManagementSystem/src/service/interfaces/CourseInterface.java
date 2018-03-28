package service.interfaces;

import java.util.List;

import persistence.domain_model.Course;

public interface CourseInterface {
	public Course getCourse(int courseId);
	public Course getCourseByName(String courseName);
	public List<Course> getCoursesByTeacherId(int teacherId);
	public List<Course> getAll();
}
