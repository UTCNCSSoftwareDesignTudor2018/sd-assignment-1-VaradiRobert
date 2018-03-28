package business.interfaces;

import java.util.List;

import persistence.domain_model.Grade;

public interface GradeDAOInterface {
	public List<Grade> findAll();
	public List<Grade> findByStudentId(int studentId);
	public void addGrade(Grade grade);
}
