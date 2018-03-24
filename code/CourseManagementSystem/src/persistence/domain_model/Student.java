package persistence.domain_model;

import java.util.List;

public class Student extends User {
	private int identityCardNumber;
	private String personalNumericalCode;
	private String address;
	private List<Grade> grades;
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
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}
