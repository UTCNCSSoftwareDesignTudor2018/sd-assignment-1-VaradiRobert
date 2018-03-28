package business.business_logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import business.interfaces.StudentDAOInterface;
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
import service.interfaces.CourseInterface;
import service.interfaces.EnrollmentInterface;
import service.interfaces.ExamInterface;
import service.interfaces.GradeInterface;
import service.interfaces.GroupInterface;
import service.interfaces.StudentInterface;

public class StudentBLL implements StudentInterface {
	private List<Validator<Student>> validators;
	private StudentDAOInterface studentDAO;
	private String generatePersonalNumericalCode() {
		String uniqueId = UUID.randomUUID().toString();
		String personalNumericalCode = "";
		int length = uniqueId.length();
		for(int i = 0; i < length; i++) {
			if(!"abcedfghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(new String(new char[] {uniqueId.charAt(i)}))) {
				personalNumericalCode += uniqueId.charAt(i);
			}
		}
		return personalNumericalCode;
	}
	
	private void setValidators() {
		validators = new ArrayList<Validator<Student>>();
		validators.add(new StudentUserNameValidator());
		validators.add(new StudentPasswordValidator());
		validators.add(new StudentEmailValidator());
		validators.add(new StudentFirstNameValidator());
		validators.add(new StudentLastNameValidator());
		validators.add(new StudentPhoneValidator());
	}
	
	public StudentBLL() {
		this.studentDAO = new StudentDAO();
		setValidators();
	}
	
	private void validate(Student student) {
		for(Validator<Student> v : validators) {
			v.validate(student);
		}
	}

	@Override
	public void createProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address) {
		PasswordEncrypter pe = new PasswordEncrypter();
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
		student.setIdentityCardNumber(studentDAO.getRecordCount() + 1);
		GroupInterface groupBLL = new GroupBLL();
		List<Group> groups = groupBLL.getAll();
		for(Group group : groups) {
			group.setStudents(studentDAO.findAllByGroupId(group.getId()));
		}
		int groupIdToEnroll = 1;
		int minimumStudentCount = groups.get(0).getStudents().size();
		int numberOfGroups = groups.size();
		for(int i = 1; i < numberOfGroups; i++) {
			int studentCount = groups.get(i).getStudents().size();
			if(studentCount < minimumStudentCount) {
				minimumStudentCount = studentCount;
				groupIdToEnroll = groups.get(i).getId();
			}
		}
		student.setGroupId(groupIdToEnroll);
		studentDAO.createStudent(student);;		
		CourseInterface courseBLL = new CourseBLL();
		List<Course> courses = courseBLL.getAll();
		GradeInterface gradeBLL = new GradeBLL();
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		for(Course c : courses) {
			Enrollment enrollment = new Enrollment();
			enrollment.setStudentId(student.getIdentityCardNumber());
			enrollment.setCourseId(c.getId());
			enrollment.setStatus(EnrollmentBLL.STATUS_UNENROLLED);
			enrollmentBLL.saveEnrollment(enrollment);
			Grade grade = new Grade();
			grade.setCourseId(c.getId());
			grade.setStudentId(student.getIdentityCardNumber());
			grade.setDate(new Date(Calendar.getInstance().getTime().getTime()));
			grade.setValue(-1);
			gradeBLL.saveGrade(grade);
		}
	}

	@Override
	public Student getProfile(String userName) {
		return studentDAO.findByUserName(userName);
	}

	@Override
	public boolean login(String userName, String password) {
		PasswordEncrypter pe = new PasswordEncrypter();
		Student student = studentDAO.findByUserName(userName);
		if(student == null) {
			return false;
		}
		if(pe.match(password, student.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public void updateProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address) {
		Student student = studentDAO.findByUserName(userName);
		if(password.equals(passwordAgain) && !password.equals("")) {
			student.setPassword(password);
		}
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPhone(phone);
		student.setAddress(address);
		validate(student);
		studentDAO.updateStudent(student);
	}

	@Override
	public void sendEnrollmentRequest(String userName, String courseName) {
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		Student student = studentDAO.findByUserName(userName);
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		enrollmentBLL.sendEnrollmentRequest(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public void unenrollFromCourse(String userName, String courseName) {
		Student student = studentDAO.findByUserName(userName);
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		enrollmentBLL.unenrollStudent(student.getIdentityCardNumber(), course.getId());
	}

	@Override
	public Group getGroup(String userName) {
		Student student = studentDAO.findByUserName(userName);
		GroupInterface groupBLL = new GroupBLL();
		Group group = groupBLL.getByGroupId(student.getGroupId());
		group.setStudents(studentDAO.findAllByGroupId(group.getId()));
		return group;
	}

	@Override
	public List<Exam> getExams(String userName) {
		ExamInterface examBLL = new ExamBLL();
		List<Enrollment> enrollments = getEnrollments(userName);
		List<Exam> exams = new ArrayList<Exam>();
		for(Enrollment enrollment : enrollments) {
			exams.add(examBLL.getExamByCourseId(enrollment.getCourseId()));
		}
		return exams;
	}

	@Override
	public List<Course> getCourses(String userName) {
		List<Enrollment> enrollments = getEnrollments(userName);
		List<Course> courses = new ArrayList<Course>();
		for(Enrollment enrollment : enrollments) {
			courses.add(enrollment.getCourse());
		}
		return courses;
	}

	@Override
	public List<Grade> getGrades(String userName) {
		Student student = studentDAO.findByUserName(userName);
		GradeInterface gradeBLL = new GradeBLL();
		List<Grade> grades = gradeBLL.getGradesByStudentId(student.getIdentityCardNumber());
		return grades;
	}

	@Override
	public List<Enrollment> getEnrollments(String userName) {
		Student student = studentDAO.findByUserName(userName);
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		List<Enrollment> enrollments = enrollmentBLL.getEnrollments(student.getIdentityCardNumber());
		return enrollments;
	}

	@Override
	public Student getStudentByUserName(String studentName) {
		Student student = studentDAO.findByUserName(studentName);
		return student;
	}

	@Override
	public List<Student> getStudentsByGroupId(int groupId) {
		return studentDAO.findAllByGroupId(groupId);
	}

	@Override
	public Student getStudentById(int studentId) {
		return studentDAO.findById(studentId);
	}

	@Override
	public List<Student> getByEnrollments(List<Enrollment> enrollments) {
		StudentInterface studentBLL = new StudentBLL();
		List<Student> students = new ArrayList<Student>();
		for(Enrollment e : enrollments) {
			students.add(studentBLL.getStudentById(e.getStudentId()));
		}
		return students;
	}

	@Override
	public void cancelEnrollmentRequest(String loggedInUserName, String courseName) {
		Student student = studentDAO.findByUserName(loggedInUserName);
		CourseInterface courseBLL = new CourseBLL();
		Course course = courseBLL.getCourseByName(courseName);
		EnrollmentInterface enrollmentBLL = new EnrollmentBLL();
		System.err.println(student == null);
		System.err.println(course == null);
		enrollmentBLL.cancelEnrollment(student.getIdentityCardNumber(), course.getId());
	}
}
