package main_tab;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	public String id, name;
	public boolean included, required;
	public List<ClassCourse> classes = new ArrayList<ClassCourse>();
	
	public Subject(String id, String name, boolean included, boolean required) {
		this.id = id;
		this.name = name;
		this.included = included;
		this.required = required;
	}
	
	public void addClass(ClassCourse c) {
		classes.add(c);
	}
	
	public int count() {
		return classes.size();
	}
	
	public ClassCourse getCourse(int index) {
		return classes.get(index);
	}

}
