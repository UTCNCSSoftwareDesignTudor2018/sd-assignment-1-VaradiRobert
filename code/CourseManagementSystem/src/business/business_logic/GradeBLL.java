package business.business_logic;

import java.util.List;

import business.interfaces.GradeDAOInterface;
import persistence.dao.GradeDAO;
import persistence.domain_model.Grade;
import service.interfaces.CourseInterface;
import service.interfaces.GradeInterface;

public class GradeBLL implements GradeInterface {
	private GradeDAOInterface gradeDAO;
	private CourseInterface courseBLL;
	
	public GradeBLL() {
		this.gradeDAO = new GradeDAO();
	}
	
	@Override
	public List<Grade> getGradesByStudentId(int studentId) {
		List<Grade> grades = gradeDAO.findByStudentId(studentId);
		for(Grade grade : grades) {
			grade.setCourse(courseBLL.getCourse(grade.getCourseId()));
		}
		return grades;
	}
	
}
