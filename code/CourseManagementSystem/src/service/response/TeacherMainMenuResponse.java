package service.response;

import java.util.List;

import persistence.domain_model.Course;
import persistence.domain_model.Exam;
import persistence.domain_model.Group;
import persistence.domain_model.Teacher;

public class TeacherMainMenuResponse extends Response {
	private Teacher teacher;
	private List<Course> courses;
	private List<Group> groups;
	private List<Exam> exams;
}
