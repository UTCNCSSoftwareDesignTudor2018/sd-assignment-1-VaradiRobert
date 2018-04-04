package persistence.interfaces;

import java.util.List;

import persistence.entities.Grade;

public interface GradeDAOInterface {
	public List<Grade> findAll();
	public List<Grade> findByStudentId(int studentId);
	public void addGrade(Grade grade);
	public List<Grade> findByCourseId(int courseId);
	public Grade findByCourseAndStudentId(int id, int identityCardNumber);
	public void update(Grade grade);
}
