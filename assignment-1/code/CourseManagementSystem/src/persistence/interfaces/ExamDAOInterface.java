package persistence.interfaces;

import java.util.List;

import persistence.entities.Exam;

public interface ExamDAOInterface {
	public List<Exam> findAll();
	public Exam getExamById(int examId);
	public Exam getByCourseId(int courseId);
	public void updateExam(Exam exam);
}
