package business.business_logic;

import java.util.List;

import business.interfaces.GroupDAOInterface;
import persistence.dao.GroupDAO;
import persistence.domain_model.Group;
import service.interfaces.GroupInterface;
import service.interfaces.StudentInterface;

public class GroupBLL implements GroupInterface {
	private GroupDAOInterface groupDAO;
	
	public GroupBLL() {
		this.groupDAO = new GroupDAO();
	}

	@Override
	public Group getByGroupId(int groupId) {
		Group group =  groupDAO.findGroupById(groupId);
		StudentInterface studentBLL = new StudentBLL();
		group.setStudents(studentBLL.getStudentsByGroupId(group.getId()));
		return group;
	}

	@Override
	public List<Group> getAll() {
		return groupDAO.findAll();
	}
}
