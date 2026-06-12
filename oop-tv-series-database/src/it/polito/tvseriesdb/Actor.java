package it.polito.tvseriesdb;

import java.util.LinkedList;
import java.util.List;

public class Actor {
	
	private String name;
	private String surname;
	private String nationality;
	private List<TVSerie> tvSeries = new LinkedList<>();
	
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
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Actor(String name, String surname, String nationality) {
		super();
		this.name = name;
		this.surname = surname;
		this.nationality = nationality;
	}
	public boolean isBest() {
		for(TVSerie serie : tvSeries) if(serie.getScore()<=8.0) return false;
		return true;
	}
	public void addTvSerie(TVSerie serie) {
		tvSeries.add(serie);
	}
	public List<TVSerie> getTvSeries() {
		return tvSeries;
	}
}
