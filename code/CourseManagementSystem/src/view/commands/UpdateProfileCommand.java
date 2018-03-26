package view.commands;

public class UpdateProfileCommand implements Command {
	private String password;
	private String passwordAgain;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	public UpdateProfileCommand(String password, String passwordAgain, String email, String firstName, String lastName, String phone, String address) {
		this.password = password;
		this.passwordAgain = passwordAgain;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
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
