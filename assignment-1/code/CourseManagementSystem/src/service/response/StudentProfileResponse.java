package service.response;

import java.util.List;

import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Exam;
import persistence.entities.Grade;
import persistence.entities.Group;
import persistence.entities.Student;

public class StudentProfileResponse extends Response {
	private Student student;
	private Group group;
	private List<Course> courses;
	private List<Exam> exams;
	private List<Enrollment> enrollments;
	private List<Grade> grades;
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public List<Exam> getExams() {
		return exams;
	}
	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
}
