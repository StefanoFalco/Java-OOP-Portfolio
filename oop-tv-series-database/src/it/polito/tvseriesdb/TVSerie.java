package it.polito.tvseriesdb;

import java.util.LinkedList;
import java.util.List;

public class TVSerie {
	
	private String title;
	private String tService;
	private String genre;
	private List<String> actors = new LinkedList<>();
	private int numVotes=0;
	private int sumVotes=0;
	private List<Season> seasons;
	private List<Integer> indexincompleteSeasons= new LinkedList<>();
	public List<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String gettService() {
		return tService;
	}
	public void settService(String tService) {
		this.tService = tService;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public TVSerie(String title, String tService, String genre) {
		super();
		this.title = title;
		this.tService = tService;
		this.genre = genre;
	}
	
	public List<String> getActors() {
		return actors;
	}
	public void setActors(List<String> actors) {
		this.actors = actors;
	}
	public boolean incomplete() {
		for(Season s : seasons) {
			if(s.incomplete()) return true;
		}
		return false;
	}
	
	public List<Integer> getIncompleteSeasons() {

		List<Integer> res = new LinkedList<>();
	
		for(Season s : this.getSeasons()) {
			if(s.incomplete()) res.add(Integer.valueOf(this.getSeasons().indexOf(s)));
		}
		this.indexincompleteSeasons=res;
		return indexincompleteSeasons;
	}
	
	public double getScore() {
		return (double)sumVotes/numVotes;
	}
	
	public void addVote(int vote) {
		numVotes+=1;
		sumVotes+=vote;
	}
}
