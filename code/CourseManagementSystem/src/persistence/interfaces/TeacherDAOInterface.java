package persistence.interfaces;

import java.util.List;

import persistence.entities.Teacher;

public interface TeacherDAOInterface {
	public List<Teacher> findAll();
	public Teacher findById(int teacherId);
	public Teacher getTeacherByUserName(String teacherName);
}
