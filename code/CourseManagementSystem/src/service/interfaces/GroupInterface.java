package service.interfaces;

import java.util.List;

import persistence.domain_model.Group;

public interface GroupInterface {
	public List<Group> getAll();
	public Group getByGroupId(int groupId);
}
