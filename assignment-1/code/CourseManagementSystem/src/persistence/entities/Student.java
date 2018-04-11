package persistence.entities;

public class Student extends User {
	private int identityCardNumber;
	private String personalNumericalCode;
	private String address;
	private int groupId;
	private Group group;
	public int getIdentityCardNumber() {
		return identityCardNumber;
	}
	public void setIdentityCardNumber(int identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}
	public String getPersonalNumericalCode() {
		return personalNumericalCode;
	}
	public void setPersonalNumericalCode(String personalNumericalCode) {
		this.personalNumericalCode = personalNumericalCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	@Override
	public int hashCode() {
		return getUserName().hashCode();
	}
}
