package university;

import java.util.LinkedList;
import java.util.List;

public class Course {
	
	private String title;
	private String teacher;
	private int code;
	private List<Student> students= new LinkedList<>();
	private static final int maxStudents= 100;
	private List<Integer> grades= new LinkedList<>();
	public Course(String title, String teacher, int code) {
		super();
		this.title = title;
		this.teacher = teacher;
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return String.valueOf(code) + "," + title + "," + teacher;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Integer> getGrades() {
		return grades;
	}
	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}
	
	
	
	
	
	

}
