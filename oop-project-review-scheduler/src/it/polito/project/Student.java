package it.polito.project;

public class Student {
	private String email;
	private String name;
	private String surname;
	
	public Student(String email, String name, String surname) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@Override
	public String toString() {
		return "Student [email=" + email + ", name=" + name + ", surname="
				+ surname + "]";
	}
	
	

}
