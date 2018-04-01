package business.business_logic;

import business.interfaces.CourseInterface;
import business.interfaces.ExamInterface;
import persistence.dao.ExamDAO;
import persistence.domain_model.Exam;
import persistence.interfaces.ExamDAOInterface;

public class ExamBLL implements ExamInterface {
	private ExamDAOInterface examDAO;
	public ExamBLL() {
		this.examDAO = new ExamDAO();
	}
	
	@Override
	public Exam getExam(int examId) {
		Exam exam = examDAO.getExamById(examId);
		CourseInterface courseBLL = new CourseBLL();
		exam.setCourse(courseBLL.getCourse(exam.getCourseId()));
		return exam;
	}

	@Override
	public Exam getExamByCourseId(int courseId) {
		Exam exam = examDAO.getByCourseId(courseId);
		CourseInterface courseBLL = new CourseBLL();
		exam.setCourse(courseBLL.getCourse(courseId));
		return exam;
	}
}
