package view.views;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;

import utilities.Observable;
import utilities.Observer;

@SuppressWarnings("serial")
public abstract class View extends JFrame {
	private Container contentPane;
	private Observable subject;
	
	public View(String windowName, int xPos, int yPos, int width, int height, Observer observer) {
		this.subject = new Observable(observer);
		this.setName(windowName);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		this.setBounds(xPos, yPos, width, height);
		this.setVisible(true);
	}
	
	public void close() {
		this.dispose();
	}
	
	public void put(JComponent component, int xPos, int yPos, int width, int height) {
		contentPane.add(component);
		component.setBounds(xPos, yPos, width, height);
	}
	
	public Observable getSubject() {
		return subject;
	}
}
