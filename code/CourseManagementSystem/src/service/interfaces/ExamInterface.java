package service.interfaces;

import persistence.domain_model.Exam;

public interface ExamInterface {
	public Exam getExam(int examId);
	public Exam getExamByCourseId(int courseId);
}
