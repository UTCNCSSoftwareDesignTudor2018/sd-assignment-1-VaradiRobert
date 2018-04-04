package service.response;

import java.util.List;

import persistence.entities.Enrollment;

public class ViewCoursesResponse extends Response {
	private List<Enrollment> enrollments;
	public ViewCoursesResponse(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public List<Enrollment> getCourses() {
		return enrollments;
	}
}
