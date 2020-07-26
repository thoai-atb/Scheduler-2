package main_tab;

import java.util.ArrayList;
import java.util.List;

public class ClassCourse { // Can't name it "Class"
	
	public String id;
	public Subject subject;
	public List<Section> sections = new ArrayList<Section>();
	
	public ClassCourse(String id, Subject subject) {
		this.id = id;
		this.subject = subject;
	}
	
	public String getName() {
		return subject.name;
	}
	
	public void addSection(String day, String start, String duration) {
		sections.add(new Section(day, start, duration));
	}
	
	public int sectionCount() {
		return sections.size();
	}
	
	@Override
	public String toString() {
		return "CLASS ID: " + this.id + " Number of sections: " + sections.size();
	}
}
