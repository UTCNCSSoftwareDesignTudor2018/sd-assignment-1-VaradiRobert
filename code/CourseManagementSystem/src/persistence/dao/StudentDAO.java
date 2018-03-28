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

import business.interfaces.StudentDAOInterface;
import persistence.connection.ConnectionFactory;
import persistence.domain_model.Student;

public class StudentDAO implements StudentDAOInterface {

	private List<Student> createList(ResultSet resultSet) {
		List<Student> students = new ArrayList<Student>();
		try {
			while(resultSet.next()) {
				Student student = new Student();
				try {
					student.setIdentityCardNumber((int)resultSet.getObject("identity_card_number"));
					student.setPersonalNumericalCode((String)resultSet.getObject("personal_numerical_code"));
					student.setFirstName((String)resultSet.getObject("first_name"));
					student.setLastName((String)resultSet.getObject("last_name"));
					student.setEmail((String)resultSet.getObject("email"));
					student.setPassword((String)resultSet.getObject("password"));
					student.setPhone((String)resultSet.getObject("phone"));
					student.setAddress((String)resultSet.getObject("address"));
					student.setUserName((String)resultSet.getObject("user_name"));
					student.setGroupId((int)resultSet.getObject("group_id"));
					students.add(student);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	@Override
	public List<Student> findAll() {
		String sqlQuery = "SELECT * FROM `course_management`.`students`";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new ArrayList<Student>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();
			students = createList(resultSet);
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
		return students;
	}

	@Override
	public Student findByUserName(String userName) {
		String sqlQuery = "SELECT * FROM `course_management`.`students` WHERE `user_name` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new ArrayList<Student>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			students = createList(resultSet);
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
		if(students.size() > 0) {
			return students.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public Student findById(int studentId) {
		String sqlQuery = "SELECT * FROM `course_management`.`students` WHERE `identity_card_number` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new ArrayList<Student>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, studentId);
			resultSet = statement.executeQuery();
			students = createList(resultSet);
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
		if(students.size() > 0) {
			return students.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void createStudent(Student student) {
		String sqlQuery = "INSERT INTO `course_management`.`students` "
				+ "(`identity_card_number`, `personal_numerical_code`, `user_name`, `password`, `first_name`, `last_name`, `email`, `phone`, `address`, `group_id`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, student.getIdentityCardNumber());
			statement.setString(2, student.getPersonalNumericalCode());
			statement.setString(3, student.getUserName());
			statement.setString(4, student.getPassword());
			statement.setString(5, student.getFirstName());
			statement.setString(6, student.getLastName());
			statement.setString(7, student.getEmail());
			statement.setString(8, student.getPhone());
			statement.setString(9, student.getAddress());
			statement.setInt(10, student.getGroupId());
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
	public void updateStudent(Student student) {
		String sqlQuery = "UPDATE `course_management`.`students` "
				+ "SET `personal_numerical_code` = ?,"
				+ " `user_name` = ?,"
				+ " `password` = ?,"
				+ " `first_name` = ?,"
				+ " `last_name` = ?,"
				+ " `email` = ?,"
				+ " `phone` = ?,"
				+ " `address` = ?"
				+ " WHERE `identity_card_number` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(9, student.getIdentityCardNumber());
			statement.setString(1, student.getPersonalNumericalCode());
			statement.setString(2, student.getUserName());
			statement.setString(3, student.getPassword());
			statement.setString(4, student.getFirstName());
			statement.setString(5, student.getLastName());
			statement.setString(6, student.getEmail());
			statement.setString(7, student.getPhone());
			statement.setString(8, student.getAddress());
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
	public void deleteStudentByUserName(String userName) {
		String sqlQuery = "DELETE FROM `course_management`.`students` WHERE `user_name` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setString(1, userName);
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
	public int getRecordCount() {
		return findAll().size();
	}

	@Override
	public List<Student> findAllByGroupId(int groupId) {
		String sqlQuery = "SELECT * FROM `course_management`.`students` WHERE `group_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Student> students = new ArrayList<Student>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, groupId);
			resultSet = statement.executeQuery();
			students = createList(resultSet);
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
		return students;
	}
	
}
