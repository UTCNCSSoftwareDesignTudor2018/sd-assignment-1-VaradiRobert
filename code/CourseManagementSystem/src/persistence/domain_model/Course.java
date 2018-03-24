package persistence.domain_model;

import java.util.Map;

public class Course {
	private int id;
	private String name;
	private int credits;
	private Map<String, Student> enrolledStudents;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public Map<String, Student> getEnrolledStudents() {
		return enrolledStudents;
	}
	public void setEnrolledStudents(Map<String, Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
