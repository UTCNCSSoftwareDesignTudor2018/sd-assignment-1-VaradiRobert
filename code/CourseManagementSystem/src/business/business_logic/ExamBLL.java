package business.business_logic;

import java.util.ArrayList;
import java.util.List;

import persistence.dao.ExamDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Exam;
import persistence.domain_model.Teacher;

public class ExamBLL {
	private ExamDAO examDAO;
	private CourseBLL courseBLL;
	private TeacherBLL teacherBLL;
	public ExamBLL() {
		this.examDAO = new ExamDAO();
		this.courseBLL = new CourseBLL();
		this.teacherBLL = new TeacherBLL();
	}
	
	public Exam getExam(int examId) {
		List<Exam> exams = examDAO.getAllObjectsWhere(e -> ((Exam)e).getId() == examId);
		Exam e = exams.get(0);
		e.setCourse(courseBLL.getCourseById(e.getCourseId()));
		e.setTeacher(teacherBLL.getTeacherById(e.getTeacherId()));
		return e;
	}
	
	public Exam getExam(Course course, Teacher teacher) {
		return examDAO.getAllObjectsWhere(e -> ((Exam)e).getCourseId() == course.getId() && ((Exam)e).getTeacherId() == teacher.getId()).get(0);
	}
	
	public List<Course> getCourses(Teacher teacher) {
		List<Exam> exams = examDAO.getAllObjectsWhere(e -> ((Exam)e).getTeacherId() == teacher.getId());
		List<Course> courses = new ArrayList<Course>();
		for(Exam e : exams) {
			courses.add(courseBLL.getCourseById(e.getCourseId()));
		}
		return courses;
	}
	
	public List<Exam> getExams(Teacher teacher) {
		List<Exam> exams = examDAO.getAllObjectsWhere(e -> ((Exam)e).getTeacherId() == teacher.getId());
		for(Exam e : exams) {
			e.setCourse(courseBLL.getCourseById(e.getCourseId()));
			e.setTeacher(teacher);
		}
		return exams;
	}
}
