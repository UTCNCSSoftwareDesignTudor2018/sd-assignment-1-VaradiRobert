package persistence.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import persistence.connection.ConnectionFactory;

public abstract class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private static final String DATABASE_NAME = "course_management";
	private final Class<T> type;
	private final Field[] declaredFields;
	private final Field[] inheritedFields;
	private DAOConfiguration daoConfiguration;
	
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		declaredFields = type.getDeclaredFields();
		inheritedFields = type.getSuperclass().getDeclaredFields();
		daoConfiguration = (new XMLConfigurationFileParser()).parse(type.getSimpleName());
	}
	
	protected List<T> createObjects(ResultSet resultSet) {
		List<T> objects = new ArrayList<T>();
		try {
			while(resultSet.next()) {
				T instance;
				instance = type.getConstructor().newInstance();
				for(Field field : declaredFields) {
					Column column = daoConfiguration.getMapping().get(field.getName());
					if(column != null) {
						String columnName = column.getColumnName();
						Object value = null;
						value = resultSet.getObject(columnName);
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
						Method writeMethod = propertyDescriptor.getWriteMethod();
						writeMethod.invoke(instance, value);
					}
				}
				for(Field field : inheritedFields) {
					Column column = daoConfiguration.getMapping().get(field.getName());
					if(column != null) {
						String columnName = column.getColumnName();
						Object value = null;
						value = resultSet.getObject(columnName);
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
						Method writeMethod = propertyDescriptor.getWriteMethod();
						writeMethod.invoke(instance, value);
					}
				}
				objects.add(instance);
				
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	public List<T> getAllObjects() {
		String selectQuery = "SELECT * FROM `" + DATABASE_NAME + "`.`" + type.getSimpleName().toLowerCase() + '`';
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionFactory.getConnection();
			statement = (PreparedStatement)connection.prepareStatement(selectQuery);
			resultSet = statement.executeQuery();
		} catch (SQLException e1) {
			
		}
		List<T> objects = createObjects(resultSet);
		try {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			
		}
		return objects;
	}
	
	public List<T> getAllObjectsWhere(Predicate<T> filter) {
		return getAllObjects().parallelStream().filter(filter).collect(Collectors.toList());
	}
	
	private String createInsertStatement() {
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append("`" + DATABASE_NAME + "`.`" + type.getSimpleName().toLowerCase() + "` (");
		int nValues = 0;
		for(Field field : declaredFields) {
			String columnName = daoConfiguration.getMapping().get(field.getName()).getColumnName();
			if(columnName != null) {
				sb.append(columnName);
				nValues++;
			}
		}
		for(Field field : inheritedFields) {
			String columnName = daoConfiguration.getMapping().get(field.getName()).getColumnName();
			if(columnName != null) {
				sb.append(columnName);
				nValues++;
			}
		}
		sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 2));
		sb.append(")");
		sb.append(" VALUES (");
		for(int i = 0; i < nValues; i++) {
			sb.append("?" + ((i == nValues - 1) ? ")" : ", "));
		}
		return sb.toString();
	}
	
	private void set(PreparedStatement statement, Object obj, int index) {
		try {
			if(obj instanceof String) {
				statement.setString(index, (String)obj);
			} else if(obj instanceof Integer) {
				statement.setInt(index, (Integer)obj);
			} else if(obj instanceof Date) {
				statement.setDate(index, (Date)obj);
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	private void setValuesForInsert(PreparedStatement statement, T object) {
		int currentIndex = 0;
		Map<String, Object> fieldValues = getFieldValues(object);
		for(Field field : declaredFields) {
			String fieldName = field.getName();
			String columnName = daoConfiguration.getMapping().get(fieldName).getColumnName();
			if(columnName != null) {
				set(statement, fieldValues.get(fieldName), ++currentIndex);
			}
		}
		for(Field field : inheritedFields) {
			String fieldName = field.getName();
			String columnName = daoConfiguration.getMapping().get(field.getName()).getColumnName();
			if(columnName != null) {
				set(statement, fieldValues.get(fieldName), ++currentIndex);
			}
		}
	}
	
	private Map<String, Object> getFieldValues(T object) {
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		for(Field fields : declaredFields) {
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(fields.getName(), type);
			} catch (IntrospectionException e1) {
				e1.printStackTrace();
			}
			Method method = pd.getReadMethod();
			try {
				fieldValues.put(fields.getName(), method.invoke(object));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
		}
		for(Field field : inheritedFields) {
			PropertyDescriptor pd = null;
			try {
				pd = new PropertyDescriptor(field.getName(), type);
			} catch (IntrospectionException e1) {
				e1.printStackTrace();
			}
			Method method = pd.getReadMethod();
			try {
				fieldValues.put(field.getName(), method.invoke(object));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 
		}
		return fieldValues;
	}
	
	public void storeObject(T object) {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = connectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String insertStatement = createInsertStatement();
		PreparedStatement statement = null;
		try {
			statement = (PreparedStatement)connection.prepareStatement(insertStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setValuesForInsert(statement, object);
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String createUpdateString() {
		StringBuilder sb = new StringBuilder("UPDATE " + daoConfiguration.getTableName());
		sb.append(" SET ");
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && !column.isPrimaryKey()) {
				String columnName = column.getColumnName();
				sb.append(columnName + " = ?, ");
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && !column.isPrimaryKey()) {
				String columnName = column.getColumnName();
				sb.append(columnName + " = ?, ");
			}
		}
		sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 2));
		sb.append(" WHERE ");
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				String columnName = column.getColumnName();
				sb.append(columnName + " = ? ");
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				String columnName = column.getColumnName();
				sb.append(columnName + " = ? ");
			}
		}
		return sb.toString();
	}
	
	private void setValuesForUpdate(PreparedStatement statement, T object) {
		Map<String, Object> fieldValues = getFieldValues(object);
		int currentIndex = 0;
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && !column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), ++currentIndex);
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && !column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), ++currentIndex);
			}
		}
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), ++currentIndex);
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), ++currentIndex);
			}
		}
	}
	
	public void updateObject(T object) {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement = (PreparedStatement)connection.prepareStatement(createUpdateString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setValuesForUpdate(statement, object);
		try {
			statement.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String createDeleteString() {
		StringBuilder sb = new StringBuilder("DELETE FROM ");
		sb.append(daoConfiguration.getTableName());
		sb.append(" WHERE ");
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				sb.append(column.getColumnName() + " = ?");
				return sb.toString();
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				sb.append(column.getColumnName() + " = ?");
				return sb.toString();
			}
		}
		return null;
	}
	
	private void setValuesForDelete(PreparedStatement statement, T object) {
		Map<String, Object> fieldValues = getFieldValues(object);
		for(Field field : declaredFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), 1);
				return;
			}
		}
		for(Field field : inheritedFields) {
			Column column = daoConfiguration.getMapping().get(field.getName());
			if(column != null && column.isPrimaryKey()) {
				set(statement, fieldValues.get(field.getName()), 1);
				return;
			}
		}
	}
	
	public void deleteObject(T object) {
		ConnectionFactory connectionFactory = null;
		try {
			connectionFactory = ConnectionFactory.getSingleInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = connectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PreparedStatement statement = null;
		try {
			statement = (PreparedStatement)connection.prepareStatement(createDeleteString());
			setValuesForDelete(statement, object);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
