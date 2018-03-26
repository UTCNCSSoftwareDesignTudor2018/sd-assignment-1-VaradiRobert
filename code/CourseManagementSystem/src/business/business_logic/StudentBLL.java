package business.business_logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import business.security.PasswordEncrypter;
import business.validators.Validator;
import business.validators.student.StudentEmailValidator;
import business.validators.student.StudentFirstNameValidator;
import business.validators.student.StudentLastNameValidator;
import business.validators.student.StudentPasswordValidator;
import business.validators.student.StudentPhoneValidator;
import business.validators.student.StudentUserNameValidator;
import persistence.dao.StudentDAO;
import persistence.domain_model.Course;
import persistence.domain_model.Enrollment;
import persistence.domain_model.Exam;
import persistence.domain_model.Grade;
import persistence.domain_model.Group;
import persistence.domain_model.Student;
import persistence.domain_model.Teacher;
import service.interfaces.StudentInterface;

public class StudentBLL implements StudentInterface {
	private PasswordEncrypter pe;
	private StudentDAO studentDAO;
	private GroupBLL groupBLL;
	///private EnrollmentBLL enrollmentBLL;
	private GradeBLL gradeBLL;
	private List<Validator<Student>> validators;
	private static int recordCount = 0;
	
	private String generatePersonalNumericalCode() {
		String uniqueId = UUID.randomUUID().toString();
		String personalNumericalCode = "";
		int length = uniqueId.length();
		for(int i = 0; i < length; i++) {
			if(!"abcedfghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(uniqueId.substring(i, 1))) {
				personalNumericalCode += uniqueId.charAt(i);
			}
		}
		return personalNumericalCode;
	}
	
	public StudentBLL() {
		this.studentDAO = new StudentDAO();
		this.groupBLL = new GroupBLL();
		//this.enrollmentBLL = new EnrollmentBLL();
		this.gradeBLL = new GradeBLL();
		validators = new ArrayList<Validator<Student>>();
		validators.add(new StudentUserNameValidator());
		validators.add(new StudentPasswordValidator());
		validators.add(new StudentEmailValidator());
		validators.add(new StudentFirstNameValidator());
		validators.add(new StudentLastNameValidator());
		validators.add(new StudentPhoneValidator());
	}
	
	private void validate(Student student) {
		for(Validator<Student> v : validators) {
			v.validate(student);
		}
	}

	@Override
	public void createProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address) {
		Student student = new Student();
		student.setUserName(userName);
		if(password.equals(passwordAgain)) {
			student.setPassword(pe.encrypt(password));
		} else {
			throw new IllegalArgumentException("Password confirmation exception!");
		}
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPhone(phone);
		student.setAddress(address);
		validate(student);
		student.setPersonalNumericalCode(generatePersonalNumericalCode());
		student.setIdentityCardNumber(recordCount + 1);
		
	}

	@Override
	public Student viewProfile(String userName) {
		return getStudentByUserName(userName);
	}

	@Override
	public void sendEnrollmentRequest(String userName, String courseName, String teacherName) {
		// TODO
		Student student = getStudentByUserName(userName);
		///enrollmentBLL.enrollStudent(student, courseName);
	}

	@Override
	public void unenrollFromCourse(String userName, String courseName, String teacherName) {
		// TODO
		Student student = getStudentByUserName(userName);
		///enrollmentBLL.unenrollStudent(student, courseName);
	}

	@Override
	public Group viewGroup(String userName) {
		Student student = getStudentByUserName(userName);
		if(student == null) {
			return null;
		}
		Group group = groupBLL.getGroupById(student.getGroupId());
		return group;
	}

	@Override
	public boolean login(String userName, String password) {
		List<Student> students = studentDAO.getAllObjectsWhere(s -> ((Student)s).getUserName().equals(userName) && pe.match(password, ((Student)s).getPassword()));
		return students.size() > 0;
	}

	@Override
	public List<Exam> getExams(String userName) {
		List<Grade> grades = getGrades(userName);
		List<Exam> exams = new ArrayList<Exam>();
		for(Grade g : grades) {
			exams.add(g.getExam());
		}
		return exams;
	}

	@Override
	public List<Course> getCourses(String userName) {
		List<Exam> exams = getExams(userName);
		List<Course> courses = new ArrayList<Course>();
		for(Exam e : exams) {
			courses.add(e.getCourse());
		}
		return courses;
	}

	@Override
	public Map<Course, Teacher> getTeachers(String userName) {
		List<Exam> exams = getExams(userName);
		Map<Course, Teacher> teachers = new HashMap<Course, Teacher>();
		for(Exam e : exams) {
			teachers.put(e.getCourse(), e.getTeacher());
		}
		return teachers;
	}

	@Override
	public List<Enrollment> getEnrollments(String userName) {
		Student student = getStudentByUserName(userName);
		//List<Enrollment> enrollments = enrollmentBLL.getEnrollmentsForStudent(student);
		return null;
	}
	
	@Override
	public List<Grade> getGrades(String userName) {
		Student student = getStudentByUserName(userName);
		List<Grade> grades = gradeBLL.getGrades(student);
		return grades;
	}
	
	public Student getStudentByUserName(String userName) {
		List<Student> students = studentDAO.getAllObjectsWhere(s -> ((Student)s).getUserName().equals(userName));
		if(students.size() > 0) {
			return students.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address) {
		Student student = getStudentByUserName(userName);
		if(password.equals(passwordAgain)) {
			student.setPassword(pe.encrypt(password));
		} else {
			throw new IllegalArgumentException("Password confirmation exception!");
		}
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPhone(phone);
		student.setAddress(address);
		validate(student);
		studentDAO.updateObject(student);
	}
	
	public List<Student> getStudents(List<Enrollment> enrollments) {
		List<Student> students = studentDAO.getAllObjectsWhere(s -> {
			for(Enrollment e : enrollments) {
				if(((Student)s).getIdentityCardNumber() == e.getStudentId()) {
					return true;
				}
			}
			return false;
		});
		return students;
	}
}
