package persistence.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import business.interfaces.EnrollmentDAOInterface;
import persistence.connection.ConnectionFactory;
import persistence.domain_model.Enrollment;

public class EnrollmentDAO implements EnrollmentDAOInterface {

	private List<Enrollment> createList(ResultSet resultSet) {
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			while(resultSet.next()) {
				Enrollment enrollment = new Enrollment();
				try {
					enrollment.setId((int)resultSet.getObject("id"));
					enrollment.setCourseId((int)resultSet.getObject("course_id"));
					enrollment.setStudentId((int)resultSet.getObject("student_id"));
					enrollment.setStatus((String)resultSet.getObject("enrollment_status"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				enrollments.add(enrollment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enrollments;
	}
	
	@Override
	public List<Enrollment> findAll() {
		String sqlQuery = "SELECT * FROM `course_management`.`enrollments`";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();
			enrollments = createList(resultSet);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return enrollments;
	}

	@Override
	public List<Enrollment> findAllByCourseId(int courseId) {
		String sqlQuery = "SELECT * FROM `course_management`.`enrollments` WHERE `course_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, courseId);
			resultSet = statement.executeQuery();
			enrollments = createList(resultSet);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return enrollments;
	}

	@Override
	public List<Enrollment> findAllByStudentId(int studentId) {
		String sqlQuery = "SELECT * FROM `course_management`.`enrollments` WHERE `student_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, studentId);
			resultSet = statement.executeQuery();
			enrollments = createList(resultSet);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return enrollments;
	}

	@Override
	public void addEnrollment(Enrollment enrollment) {
		String sqlQuery = "INSERT INTO `course_management`.`enrollments` "
				+ "(`id`, `student_id`, `course_id`, `enrollment_status`) "
				+ "VALUES (?, ?, ?, ?)";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, enrollment.getId());
			statement.setInt(2, enrollment.getStudentId());
			statement.setInt(3, enrollment.getCourseId());
			statement.setString(4, enrollment.getStatus());
			statement.executeUpdate();
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateEnrollment(Enrollment enrollment) {
		String sqlQuery = "UPDATE `course_management`.`enrollments` "
				+ "SET `course_id` = ?,"
				+ " `student_id` = ?,"
				+ " `enrollment_status` = ?"
				+ " WHERE `id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, enrollment.getCourseId());
			statement.setInt(2, enrollment.getStudentId());
			statement.setString(3, enrollment.getStatus());
			statement.setInt(4, enrollment.getId());
			statement.executeUpdate();
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Enrollment findByStudentAndCourseIds(int studentId, int courseId) {
		String sqlQuery = "SELECT * FROM `course_management`.`enrollments` WHERE `student_id` = ? AND `course_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			System.err.println(statement.asSql());
			resultSet = statement.executeQuery();
			enrollments = createList(resultSet);
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return enrollments.get(0);
	}
	
}
