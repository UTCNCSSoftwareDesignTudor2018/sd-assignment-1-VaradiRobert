package view.commands;

public class CreateProfileCommand implements Command {
	private String userName;
	private String password;
	private String passwordAgain;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	public CreateProfileCommand(String userName, String password, String passwordAgain, String email, String firstName, String lastName, String phone, String address) {
		super();
		this.userName = userName;
		this.password = password;
		this.passwordAgain = passwordAgain;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getPasswordAgain() {
		return passwordAgain;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
}
