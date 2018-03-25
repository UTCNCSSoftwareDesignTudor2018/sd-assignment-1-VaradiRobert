package business.business_logic;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import persistence.dao.GradeDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Grade;
import persistence.domain_model.Student;

public class GradeBLL {
	private GradeDAO gradeDAO;
	private ExamBLL examBLL;
	private static int recordCount;
	public GradeBLL() {
		this.gradeDAO = new GradeDAO();
		this.examBLL = new ExamBLL();
		recordCount = gradeDAO.getAllObjects().size();
	}
	
	public List<Grade> getGrades(Student student) {
		List<Grade> grades = gradeDAO.getAllObjectsWhere(g -> ((Grade)g).getStudentId() == student.getIdentityCardNumber());
		for(Grade g : grades) {
			g.setStudent(student);
			g.setExam(examBLL.getExam(g.getExamId()));
		}
		return grades;
	}
	
	public void addGrade(Student student, Course course, int grade) {
		Grade g = new Grade();
		g.setStudentId(student.getIdentityCardNumber());
		g
	}
}
