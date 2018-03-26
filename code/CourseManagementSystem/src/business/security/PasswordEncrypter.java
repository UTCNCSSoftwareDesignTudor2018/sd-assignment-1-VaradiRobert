package business.security;

public class PasswordEncrypter {
	public boolean match(String givenPassword, String actualPassword) {
		return givenPassword.equals(actualPassword);
	}
	
	public String encrypt(String password) {
		return password;
	}
}
