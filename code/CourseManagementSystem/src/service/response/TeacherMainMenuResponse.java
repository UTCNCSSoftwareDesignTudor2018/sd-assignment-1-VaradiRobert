package service.response;

import java.util.List;

import persistence.domain_model.Course;
import persistence.domain_model.Exam;
import persistence.domain_model.Group;
import persistence.domain_model.Teacher;

public class TeacherMainMenuResponse extends Response {
	public TeacherMainMenuResponse() {
		super();
	}
	private Teacher teacher;
	private List<Course> courses;
	private List<Group> groups;
	private List<Exam> exams;
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<Exam> getExams() {
		return exams;
	}
	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}
}
