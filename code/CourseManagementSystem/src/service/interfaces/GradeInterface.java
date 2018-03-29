package service.interfaces;

import java.util.List;

import persistence.domain_model.Grade;

public interface GradeInterface {
	public List<Grade> getGradesByStudentId(int studentId);
	public List<Grade> getGradesByCourseId(int courseId);
	public void saveGrade(Grade grade);
	public Grade getGradeByCourseAndStudentId(int id, int identityCardNumber);
	public void updateGrade(Grade g);
}
