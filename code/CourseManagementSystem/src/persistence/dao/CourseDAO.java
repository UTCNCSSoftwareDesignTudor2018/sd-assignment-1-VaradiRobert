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
import persistence.entities.Course;
import persistence.interfaces.CourseDAOInterface;

public class CourseDAO implements CourseDAOInterface {

	private List<Course> createList(ResultSet resultSet) {
		List<Course> courses = new ArrayList<Course>();
		try {
			while(resultSet.next()) {
				Course course = new Course();
				try {
					course.setId((int)resultSet.getObject("id"));
					course.setTeacherId((int)resultSet.getObject("teacher_id"));
					course.setName((String)resultSet.getObject("name"));
					course.setCredits((int)resultSet.getObject("credits"));
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	@Override
	public List<Course> findAll() {
		String sqlQuery = "SELECT * FROM `course_management`.`courses`";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();
			courses = createList(resultSet);
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
		return courses;
	}

	@Override
	public Course getCourseById(int courseId) {
		String sqlQuery = "SELECT * FROM `course_management`.`courses` WHERE `id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, courseId);
			resultSet = statement.executeQuery();
			courses = createList(resultSet);
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
		return courses.get(0);
	}

	@Override
	public List<Course> getCoursesByIds(List<Integer> ids) {
		List<Course> courses = new ArrayList<Course>();
		for(Integer id : ids) {
			courses.add(getCourseById(id));
		}
		return courses;
	}

	@Override
	public Course getCourseByName(String courseName) {
		String sqlQuery = "SELECT * FROM `course_management`.`courses` WHERE `name` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setString(1, courseName);
			resultSet = statement.executeQuery();
			courses = createList(resultSet);
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
		return courses.get(0);
	}

	@Override
	public List<Course> findCoursesByTeacherId(int teacherId) {
		String sqlQuery = "SELECT * FROM `course_management`.`courses` WHERE `teacher_id` = ?";
		ConnectionFactory connectionFactory = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(sqlQuery);
			statement.setInt(1, teacherId);
			resultSet = statement.executeQuery();
			courses = createList(resultSet);
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
		return courses;
	}

}
