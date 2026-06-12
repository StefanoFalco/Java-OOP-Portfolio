package it.polito.tvseriesdb;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User {
	
	private String username;
	private String genre;
	private Map<String,Integer> votes = new TreeMap<>();
	private List<String> likeSeries = new LinkedList<>();
	public List<String> getLikeSeries() {
		return likeSeries;
	}
	public void setLikeSeries(List<String> likeSeries) {
		this.likeSeries = likeSeries;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public User(String username, String genre) {
		super();
		this.username = username;
		this.genre = genre;
	}
	public Map<String, Integer> getVotes() {
		return votes;
	}
}
