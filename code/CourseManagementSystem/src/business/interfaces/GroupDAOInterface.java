package business.interfaces;

import java.util.List;

import persistence.domain_model.Group;

public interface GroupDAOInterface {
	public Group findGroupById(int groupId);
	public List<Group> findAll();
	public void addGroup(Group group);
}
