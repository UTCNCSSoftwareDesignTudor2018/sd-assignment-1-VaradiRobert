package service.interfaces;

import java.util.List;

import persistence.domain_model.Grade;

public interface GradeInterface {
	public List<Grade> getGradesByStudentId(int studentId);
	public void saveGrade(Grade grade);
}
