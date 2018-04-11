package business.validators;

public interface Validator<T> {
	public boolean validate(T object);
}
