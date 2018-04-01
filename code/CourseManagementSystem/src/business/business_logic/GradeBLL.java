package business.business_logic;

import java.util.List;

import business.interfaces.CourseInterface;
import business.interfaces.GradeInterface;
import business.interfaces.StudentInterface;
import persistence.dao.GradeDAO;
import persistence.domain_model.Grade;
import persistence.domain_model.Student;
import persistence.interfaces.GradeDAOInterface;

public class GradeBLL implements GradeInterface {
	private GradeDAOInterface gradeDAO;
	public GradeBLL() {
		this.gradeDAO = new GradeDAO();
	}
	
	@Override
	public List<Grade> getGradesByStudentId(int studentId) {
		CourseInterface courseBLL = new CourseBLL();
		List<Grade> grades = gradeDAO.findByStudentId(studentId);
		for(Grade grade : grades) {
			grade.setCourse(courseBLL.getCourse(grade.getCourseId()));
		}
		return grades;
	}

	@Override
	public void saveGrade(Grade grade) {
		grade.setId(gradeDAO.findAll().size() + 1);
		gradeDAO.addGrade(grade);
	}
	
	@Override
	public void updateGrade(Grade grade) {
		gradeDAO.update(grade);
	}

	@Override
	public List<Grade> getGradesByCourseId(int courseId) {
		List<Grade> grades = gradeDAO.findByCourseId(courseId);
		StudentInterface studentBLL = new StudentBLL();
		for(Grade g : grades) {
			Student student = studentBLL.getStudentById(g.getStudentId());
			g.setStudent(student);
		}
		return grades;
	}

	@Override
	public Grade getGradeByCourseAndStudentId(int id, int identityCardNumber) {
		return gradeDAO.findByCourseAndStudentId(id, identityCardNumber);
	} 
}
