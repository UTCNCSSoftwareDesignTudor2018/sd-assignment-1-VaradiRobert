package service.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.business_logic.EnrollmentBLL;
import persistence.entities.Course;
import persistence.entities.Enrollment;
import persistence.entities.Exam;
import persistence.entities.Grade;
import persistence.entities.Group;
import persistence.entities.Student;
import persistence.entities.Teacher;

public class TeacherMainMenuResponse extends Response {
	public TeacherMainMenuResponse() {
		super();
	}
	private Teacher teacher;
	private List<Enrollment> enrollments;
	private List<Group> groups;
	private List<Exam> exams;
	private List<Course> courses;
	private Map<String, Map<String, Double>> grades;
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
		Map<Course, List<Student>> coursesAndStudents = new HashMap<Course, List<Student>>();
		for(Enrollment e : enrollments) {
			if(e.getStatus().equals(EnrollmentBLL.STATUS_ACCEPTED)) {
				List<Student> students = coursesAndStudents.get(e.getCourse());
				if(students == null) {
					students = new ArrayList<Student>();
					coursesAndStudents.put(e.getCourse(), students);
				} else {
					students.add(e.getStudent());
				}
			}
		}
		courses = new ArrayList<Course>();
		for(Course c : coursesAndStudents.keySet()) {
			c.setEnrolledStudents(coursesAndStudents.get(c));
			courses.add(c);
		}
	}
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setGrades(List<Grade> gradeList) {
		grades = new HashMap<String, Map<String, Double>>();
		for(Grade g : gradeList) {
			Map<String, Double> entry = grades.get(g.getCourse().getName());
			if(entry == null) {
				entry = new HashMap<String, Double>();
				grades.put(g.getCourse().getName(), entry);
			}
			entry.put(g.getStudent().getUserName(), g.getValue());
		}
	}
	
	public Map<String, Map<String, Double>> getGrades() {
		return grades;
	}
}
