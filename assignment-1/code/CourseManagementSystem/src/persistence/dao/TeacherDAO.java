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

import persistence.connection.ConnectionFactory;
import persistence.entities.Teacher;
import persistence.interfaces.TeacherDAOInterface;

public class TeacherDAO implements TeacherDAOInterface {

	private List<Teacher> createList(ResultSet resultSet) {
		List<Teacher> teachers = new ArrayList<Teacher>();
		try {
			while(resultSet.next()) {
				Teacher teacher = new Teacher();
				try {
					teacher.setId((int)resultSet.getObject("id"));
					teacher.setFirstName((String)resultSet.getObject("first_name"));
					teacher.setLastName((String)resultSet.getObject("last_name"));
					teacher.setEmail((String)resultSet.getObject("email"));
					teacher.setPassword((String)resultSet.getObject("password"));
					teacher.setPhone((String)resultSet.getObject("phone"));
					teacher.setUserName((String)resultSet.getObject("user_name"));
					teachers.add(teacher);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teachers;
	}
	
	@Override
	public List<Teacher> findAll() {
		String sqlQuery = "SELECT * FROM `course_management`.`teachers`";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Teacher> teachers = new ArrayList<Teacher>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();
			teachers = createList(resultSet);
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
		return teachers;
	}

	@Override
	public Teacher findById(int teacherId) {
		String sqlQuery = "SELECT * FROM `course_management`.`teachers` WHERE `id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Teacher> teachers = new ArrayList<Teacher>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, teacherId);
			resultSet = statement.executeQuery();
			teachers = createList(resultSet);
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
		return teachers.get(0);
	}

	@Override
	public Teacher getTeacherByUserName(String teacherName) {
		String sqlQuery = "SELECT * FROM `course_management`.`teachers` WHERE `user_name` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Teacher> teachers = new ArrayList<Teacher>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setString(1, teacherName);
			resultSet = statement.executeQuery();
			teachers = createList(resultSet);
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
		return teachers.get(0);
	}
	
}
