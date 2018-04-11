package view.views;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;

import service.facade.ApplicationFacade;
import utilities.Observable;
import utilities.Observer;

@SuppressWarnings("serial")
public abstract class View extends JFrame {
	private Container contentPane;
	private Observable subject;
	public static final int LABEL_WIDTH = 120;
	public static final int LABEL_HEIGHT = 25;
	public static final int LABEL_X_POS = 10;
	public static final int TEXT_FIELD_WIDTH = 100;
	public static final int TEXT_FIELD_HEIGHT = 25;
	public View(String windowName, int xPos, int yPos, int width, int height, Observer observer) {
		super(windowName);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.subject = new Observable(observer);
		this.setName(windowName);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		this.setBounds(xPos, yPos, width, height);
		this.setVisible(true);
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void put(JComponent component, int xPos, int yPos, int width, int height) {
		contentPane.add(component);
		component.setBounds(xPos, yPos, width, height);
	}
	
	public Observable getSubject() {
		return subject;
	}
	
	public ApplicationFacade getObserver() {
		return (ApplicationFacade)subject.getObserver();
	}
	
	public void setObserver(Observer observer) {
		subject.setObserver(observer);
	}
}
