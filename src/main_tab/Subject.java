package main_tab;

import java.util.List;

public class Subject {
	public String id, name;
	public List<ClassCourse> classes;
	
	public Subject(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addClass(ClassCourse c) {
		classes.add(c);
	}

}
