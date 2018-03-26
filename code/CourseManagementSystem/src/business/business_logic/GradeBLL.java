package business.business_logic;

import java.util.Date;
import java.util.List;

import persistence.dao.GradeDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;

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
	
	public void addGrade(Student student, Course course, Teacher teacher, int grade) {
		Grade g = new Grade();
		Exam exam = examBLL.getExam(course, teacher);
		g.setStudentId(student.getIdentityCardNumber());
		g.setExamId(exam.getId());
		g.setValue(grade);
		g.setDate(new Date());
		g.setId(recordCount + 1);
		gradeDAO.storeObject(g);
		recordCount++;
	}
}
