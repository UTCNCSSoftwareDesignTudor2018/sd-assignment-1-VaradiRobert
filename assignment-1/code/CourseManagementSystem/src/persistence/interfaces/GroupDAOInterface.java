package persistence.interfaces;

import java.util.List;

import persistence.entities.Group;

public interface GroupDAOInterface {
	public Group findGroupById(int groupId);
	public List<Group> findAll();
	public void addGroup(Group group);
}
