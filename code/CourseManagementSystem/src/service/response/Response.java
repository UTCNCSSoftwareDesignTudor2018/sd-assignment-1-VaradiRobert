package service.response;

import java.util.List;

public class Response {
	private String viewIdentifier;
	private List<Object> objects;
	public String getViewIdentifier() {
		return viewIdentifier;
	}
	public void setViewIdentifier(String viewIdentifier) {
		this.viewIdentifier = viewIdentifier;
	}
	public List<Object> getObjects() {
		return objects;
	}
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
}
