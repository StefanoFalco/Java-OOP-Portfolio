package university;

import java.util.LinkedList;
import java.util.List;

public class Student {
	
	private String first;
	private String last;
	private int id;
	private List<Course> courses= new LinkedList<>();
	private static final int maxCourses= 25;
	private List<Integer> grades= new LinkedList<>();
	public Student(String first, String last, int id) {
		super();
		this.first = first;
		this.last = last;
		this.id = id;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return String.valueOf(id) + " " + first + " "+ last;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Integer> getGrades() {
		return grades;
	}
	public void setGrades(List<Integer> grade) {
		this.grades = grade;
	}
	public double getMedia() {
		if(grades.size()!=0) return grades.stream().mapToInt(x-> x.intValue()).average().getAsDouble();
		else return 0.0;
	}
	public double getPoints() {
        if(grades.isEmpty() || courses.isEmpty()) {
            return 0.0;
        }
        return getMedia() + ((double) grades.size() / (double) courses.size()) * 10.0;
    }
	
	
	
	
	
	
	
	

}
