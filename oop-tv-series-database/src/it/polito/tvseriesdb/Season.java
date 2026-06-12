package it.polito.tvseriesdb;

import java.util.LinkedList;
import java.util.List;

public class Season {
	
	private int seasonNumber;
	private String serie;
	private int numEpisodes;
	private int releaseDate;
	private List<String> episodeTitles = new LinkedList<>();
	
	public List<String> getEpisodeTitles() {
		return episodeTitles;
	}
	public void setEpisodeTitles(List<String> episodeTitles) {
		this.episodeTitles = episodeTitles;
	}
	public int getNumEpisodes() {
		return numEpisodes;
	}
	public void setNumEpisodes(int numEpisodes) {
		this.numEpisodes = numEpisodes;
	}
	public int getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Season(int numEpisodes, int releaseDate, String serie, int seasonNumber) {
		super();
		this.numEpisodes = numEpisodes;
		this.releaseDate = releaseDate;
		this.serie = serie;
		this.seasonNumber=seasonNumber;
	}
	
	public boolean incomplete() {
		return numEpisodes!=episodeTitles.size();
	}
	public String getSerie() {
		return serie;
	}
	public int getSeasonNumber() {
		return seasonNumber;
	}
	
	public void addEpisode(String title) {
		episodeTitles.add(title);
	}
}
