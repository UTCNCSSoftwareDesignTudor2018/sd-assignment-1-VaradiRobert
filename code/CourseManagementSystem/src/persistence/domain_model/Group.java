package persistence.domain_model;

import java.util.Map;

public class Group {
	private int id;
	private int number;
	private Map<String, Student> students;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Map<String, Student> getStudents() {
		return students;
	}
	public void setStudents(Map<String, Student> students) {
		this.students = students;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
}
