package utilities;

public class Observable {
	private Observer observer;
	public Observable(Observer observer) {
		this.observer = observer;
	}
	public Observer getObserver() {
		return observer;
	}
	public void setObserver(Observer observer) {
		this.observer = observer;
	}
}
