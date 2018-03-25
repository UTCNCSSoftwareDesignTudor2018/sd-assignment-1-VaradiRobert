package business.business_logic;

import java.util.List;

import persistence.dao.GroupDAO;
import persistence.domain_model.Group;

public class GroupBLL {
	private GroupDAO groupDAO;
	private static int recordCount = 0;
	
	public GroupBLL() {
		this.groupDAO = new GroupDAO();
	}
	
	public Group getGroupById(int groupId) {
		List<Group> groups = groupDAO.getAllObjectsWhere(g -> ((Group)g).getId() == groupId);
		if(groups.size() > 0) {
			return groups.get(0);
		} else {
			return null;
		}
	}
	
	public int getGroupCount() {
		return groupDAO.getAllObjects().size();
	}
	
	public void addGroup() {
		int groupNumber = groupDAO.getAllObjects().stream().max((g1, g2) -> {
				if(((Group)g1).getNumber() > ((Group)g2).getNumber()) {
					return ((Group)g1).getNumber();
				} else {
					return ((Group)g2).getNumber();
				}
			}).get().getNumber() + 1;
		Group group = new Group();
		group.setId(recordCount + 1);
		group.setNumber(groupNumber);
		groupDAO.storeObject(group);
		recordCount++;
	}
}
