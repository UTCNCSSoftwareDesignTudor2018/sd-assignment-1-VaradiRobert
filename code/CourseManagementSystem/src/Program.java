import java.sql.SQLException;
import java.util.List;

import persistence.dao.TeacherDAO;
import persistence.domain_model.Teacher;

public class Program {
	public static void main(String[] args) throws SQLException {
		List<Teacher> teachers = (new TeacherDAO()).getAllObjects();
		Teacher t = teachers.get(2);
		(new TeacherDAO()).deleteObject(t);
	}
}
