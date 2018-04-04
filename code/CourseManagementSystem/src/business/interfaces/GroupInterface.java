package business.interfaces;

import java.util.List;

import persistence.entities.Group;

public interface GroupInterface {
	public List<Group> getAll();
	public Group getByGroupId(int groupId);
}
