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

import business.interfaces.GradeDAOInterface;
import persistence.connection.ConnectionFactory;
import persistence.domain_model.Grade;

public class GradeDAO implements GradeDAOInterface {

	private List<Grade> createList(ResultSet resultSet) {
		List<Grade> grades = new ArrayList<Grade>();
		try {
			while(resultSet.next()) {
				Grade grade = new Grade();
				try {
					grade.setValue((double)resultSet.getObject("value"));
					grade.setCourseId((int)resultSet.getObject("course_id"));
					grade.setStudentId((int)resultSet.getObject("student_id"));
					grade.setDate(resultSet.getDate("date"));
					grade.setId((int)resultSet.getObject("id"));
					grades.add(grade);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}
	
	@Override
	public List<Grade> findAll() {
		String sqlQuery = "SELECT * FROM `course_management`.`grades`";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Grade> grades = new ArrayList<Grade>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();
			grades = createList(resultSet);
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
		return grades;
	}

	@Override
	public List<Grade> findByStudentId(int studentId) {
		String sqlQuery = "SELECT * FROM `course_management`.`grades` WHERE `student_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Grade> grades = new ArrayList<Grade>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, studentId);
			resultSet = statement.executeQuery();
			grades = createList(resultSet);
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
		return grades;
	}
	
	@Override
	public List<Grade> findByCourseId(int courseId) {
		String sqlQuery = "SELECT * FROM `course_management`.`grades` WHERE `course_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Grade> grades = new ArrayList<Grade>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, courseId);
			resultSet = statement.executeQuery();
			grades = createList(resultSet);
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
		return grades;
	}

	@Override
	public void addGrade(Grade grade) {
		String sqlQuery = "INSERT INTO `course_management`.`grades` "
				+ "(`id`, `course_id`, `student_id`, `value`, `date`) "
				+ "VALUES (?, ?, ?, ?, ?)";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, grade.getId());
			statement.setInt(2, grade.getCourseId());
			statement.setInt(3, grade.getStudentId());
			statement.setDouble(4, grade.getValue());
			statement.setDate(5, grade.getDate());
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
	public Grade findByCourseAndStudentId(int id, int identityCardNumber) {
		String sqlQuery = "SELECT * FROM `course_management`.`grades` WHERE `student_id` = ? AND `course_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Grade> grades = new ArrayList<Grade>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, identityCardNumber);
			statement.setInt(2, id);
			resultSet = statement.executeQuery();
			grades = createList(resultSet);
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
		return grades.get(0);
	}

	@Override
	public void update(Grade grade) {
		String sqlQuery = "UPDATE `course_management`.`grades` SET "
				+ " `course_id` = ?,"
				+ " `student_id` = ?,"
				+ " `value` = ?,"
				+ " `date` = ?"
				+ " WHERE `id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, grade.getCourseId());
			statement.setInt(2, grade.getStudentId());
			statement.setDouble(3, grade.getValue());
			statement.setDate(4, grade.getDate());
			statement.setInt(5, grade.getId());
			System.err.println(statement.asSql());
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

	
}
