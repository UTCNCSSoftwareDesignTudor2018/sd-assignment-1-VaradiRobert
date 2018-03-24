package persistence.domain_model;

import java.util.Map;

public class Teacher extends User {
	private int id;
	private Map<String, Course> courses;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<String, Course> getCourses() {
		return courses;
	}
	public void setCourses(Map<String, Course> courses) {
		this.courses = courses;
	}
}
