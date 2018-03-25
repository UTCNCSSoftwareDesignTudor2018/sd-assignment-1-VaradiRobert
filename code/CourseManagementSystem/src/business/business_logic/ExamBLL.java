package business.business_logic;

import java.util.List;

import persistence.dao.ExamDAO;
import persistence.domain_model.Exam;

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
}
