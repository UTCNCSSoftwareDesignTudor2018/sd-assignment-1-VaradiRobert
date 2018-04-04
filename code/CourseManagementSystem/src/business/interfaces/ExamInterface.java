package business.interfaces;

import persistence.entities.Exam;

public interface ExamInterface {
	public Exam getExam(int examId);
	public Exam getExamByCourseId(int courseId);
}
