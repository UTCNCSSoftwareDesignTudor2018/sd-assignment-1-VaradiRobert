package business.interfaces;

import java.util.List;

import persistence.domain_model.Student;

public interface StudentDAOInterface {
	public List<Student> findAll();
	public Student findByUserName(String userName);
	public Student findById(int studentId);
	public List<Student> findAllByGroupId(int groupId);
	public void createStudent(Student student);
	public void updateStudent(Student student);
	public void deleteStudentByUserName(String userName);
	public int getRecordCount();
}
