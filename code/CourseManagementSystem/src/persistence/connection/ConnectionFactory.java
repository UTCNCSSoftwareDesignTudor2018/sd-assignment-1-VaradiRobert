package persistence.connection;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectionFactory {
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String CONNECTION_CONFIGURATION_FILE = "./src/persistence/connection/connection.config.xml";
	private static final String DRIVER = "driver";
	private static final String DBURL = "dburl";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static String driver;
	private static String dbUrl;
	private static String user;
	private static String password;
	private static ConnectionFactory singleInstance;
	
	private ConnectionFactory() throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		File connectionConfigurationFile = new File(CONNECTION_CONFIGURATION_FILE);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(connectionConfigurationFile);
		doc.getDocumentElement().normalize();
		driver = doc.getElementsByTagName(DRIVER).item(0).getTextContent();
		dbUrl = doc.getElementsByTagName(DBURL).item(0).getTextContent();
		user = doc.getElementsByTagName(USER).item(0).getTextContent();
		password = doc.getElementsByTagName(PASSWORD).item(0).getTextContent();
		try {
			Class.forName(driver);
			System.out.println("Connection successful...");
		} catch(ClassNotFoundException e) {
			System.out.println("Connection failed!");
			throw e;
		}
	}
	
	public static ConnectionFactory getSingleInstance() throws ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
		if(singleInstance == null) {
			singleInstance = new ConnectionFactory();
		}
		return singleInstance;
	}
	
	public Connection getConnection() throws SQLException {
		return singleInstance.createConnection();
	}
	
	private Connection createConnection() throws SQLException {
		Connection connection = null;
		try {
			connection = (Connection)DriverManager.getConnection(dbUrl, user, password);
			System.out.println("Connected...");
		} catch(SQLException e) {
			LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database!");
			throw e;
		}
		return connection;
	}
	
	public static void close(Connection connection) throws SQLException {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Connection closed...");
			} catch(SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection!");
				throw e;
			}
		}
	}
	
	public static void close(Statement statement) throws SQLException {
		if(statement != null) {
			try {
				statement.close();
				System.out.println("Statement closed...");
			} catch(SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement!");
				throw e;
			}
		}
	}
	
	public static void close(ResultSet resultSet) throws SQLException {
		if(resultSet != null) {
			try {
				resultSet.close();
				System.out.println("ResultSet closed...");
			} catch(SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the result set!");
				throw e;
			}
		}
	}
}
