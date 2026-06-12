package it.polito.tvseriesdb;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class TVSeriesDB {
	
	private List<String> trasmissionServices = new LinkedList<>();
	private Map<String,TVSerie> tvseries = new TreeMap<>();
	private Map<String,Actor> actors = new TreeMap<>();
	private Map<String,User> users = new TreeMap<>();

	// R1
	
	/**
	 * adds a list of transmission services.
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param tServices the transmission services
	 * @return number of transmission service inserted so far
	 */
	public int addTransmissionService(String...tServices) {
		for(String service : tServices) {
			if(!trasmissionServices.contains(service)) {
				trasmissionServices.add(service);
			}
		}
		return trasmissionServices.size();
	}
	
	/**
	 * adds a TV series whose name is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param title the title of the TV Series
	 * @param tService the name of the transmission service 
	 * broadcasting the TV series.
	 * @param genre the genre of the TV Series
	 * @return number of TV Series inserted so far
	 * @throws TSException if TV Series is already inserted or transmission service does not exist.
	 */
	public int addTVSeries(String title, String tService, String genre) throws TSException {
		if(trasmissionServices.contains(tService)) {
			TVSerie t = new TVSerie(title,tService,genre);
			if(tvseries.containsKey(title)) throw new TSException();
			else tvseries.put(title,t);
		}
		else throw new TSException();
		return tvseries.size();
	}
	
	/**
	 * adds an actor whose name and surname is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param name the name of the actor
	 * @param surname the surname of the actor
	 * @param nationality the nationality of the actor
	 * @return number of actors inserted so far
	 * @throws TSException if actor has already been inserted.
	 */
	public int addActor(String name, String surname, String nationality) throws TSException {
		String compare = name + " " + surname;
		if(actors.containsKey(compare)) throw new TSException();
		else {
			Actor a = new Actor(name,surname,nationality);
			actors.put(compare, a);
		}
		return actors.size();
	}
	
	/**
	 * adds a cast of actors to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the cast is inserted
	 * @param actors	list of actors to add to a TV series, format of 
	 * 					each actor identity is "name surname"
	 * @return number of actors in the cast
	 * @throws TSException in case of non-existing actor or TV Series does not exist
	 */
	public int addCast(String tvSeriesTitle, String...actors) throws TSException {
		if(tvseries.containsKey(tvSeriesTitle)) {
			List<String> list = tvseries.get(tvSeriesTitle).getActors();
			for(String a: actors) {
				if(!this.actors.containsKey(a)) throw new TSException();
				else {
					list.add(a);
					this.actors.get(a).addTvSerie(tvseries.get(tvSeriesTitle));
				}
			}
			tvseries.get(tvSeriesTitle).setActors(list);
			return list.size();
		}
		else throw new TSException();
	}
      
	// R2
	
	/**
	 * adds a season to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numEpisodes	number of episodes of the season
	 * @param releaseDate	release date for the season (format "gg:mm:yyyy")
	 * @return number of seasons inserted so far for the TV series
	 * @throws TSException in case of non-existing TV Series or wrong releaseDate
	 */
	public int addSeason(String tvSeriesTitle, int numEpisodes, String releaseDate) throws TSException {
		

		String[] date = releaseDate.split(":");
		int intDate = Integer.parseInt(date[0]) + Integer.parseInt(date[1])*29 + Integer.parseInt(date[2])*365;
		Season max;

		if(tvseries.containsKey(tvSeriesTitle)) {

			List<Season> list = tvseries.get(tvSeriesTitle).getSeasons();
			if(list==null){
				list= new LinkedList<>();
				 list.add(new Season(numEpisodes,intDate,tvSeriesTitle,1));
				 tvseries.get(tvSeriesTitle).setSeasons(list);
				 return tvseries.get(tvSeriesTitle).getSeasons().size();
			}
			max= tvseries.get(tvSeriesTitle).getSeasons().stream().max(Comparator.comparingInt(Season::getReleaseDate)).orElseGet(null);
			if(max.getReleaseDate()>intDate) throw new TSException();
			else {
				list.add(new Season(numEpisodes,intDate,tvSeriesTitle,tvseries.get(tvSeriesTitle).getSeasons().size()+1));
				tvseries.get(tvSeriesTitle).setSeasons(list);
				return tvseries.get(tvSeriesTitle).getSeasons().size();
			}
		}
		else throw new TSException();
	}
	

	/**
	 * adds an episode to a season of a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numSeason	number of season to which add an episode
	 * @param episodeTitle	title of the episode (unique)
	 * @return number of episodes inserted so far for that season of the TV series
	 * @throws TSException in case of non-existing TV Series, season, repeated title 
	 * 			of the episode or exceeding number of episodes inserted
	 */
	public int addEpisode(String tvSeriesTitle, int numSeason, String episodeTitle) throws TSException {
		
		Season s;

		if(tvseries.containsKey(tvSeriesTitle)) {
			if(tvseries.get(tvSeriesTitle).getSeasons()==null) throw new TSException();
			if(tvseries.get(tvSeriesTitle).getSeasons().size()>=numSeason) {
				s = tvseries.get(tvSeriesTitle).getSeasons().get(numSeason-1);
				List<String> list= s.getEpisodeTitles();
				if(list==null) {
					s.addEpisode(episodeTitle);
				}
				else {
					if(list.size()>= s.getNumEpisodes()) throw new TSException();
					for(String ep : list) {
						if(ep.equals(episodeTitle)) throw new TSException();
					}
					s.addEpisode(episodeTitle);
				}
			}
			else throw new TSException();
		}
		else throw new TSException();
		return s.getEpisodeTitles().size();
	}

	/**
	 * check which series and which seasons are still lacking
	 * episodes information
	 * 
	 * @return map with TV series and a list of seasons missing episodes information
	 * 
	 */
	public Map<String, List<Integer>> checkMissingEpisodes() {

		Map<String,List<Integer>> res = new TreeMap<>();
		for(TVSerie tv : tvseries.values()) {
			if(tv.getSeasons()!=null) {
				List<Integer> list= tv.getSeasons().stream().filter(Season::incomplete).collect(Collectors.mapping(x -> tv.getSeasons().indexOf(x)+1, Collectors.toList()));
				if(list.size()!=0) {
					 res.put(tv.getTitle(), list);
				}
			}
		}
		return res;
	}

	// R3
	
	/**
	 * Add a new user to the database, with a unique username.
	 * 
	 * @param username	username of the user
	 * @param genre		user favourite genre
	 * @return number of registered users
	 * @throws TSException in case username is already registered
	 */
	public int addUser(String username, String genre) throws TSException {

		if(users.containsKey(username)) throw new TSException();
		else users.put(username, new User(username,genre));
		return users.size();
	}

	/**
	 * Adds a series to the list of favourite
	 * series of a user.
	 * 
	 * @param username	username of the user
	 * @param tvSeriesTitle	 title of TV series to add to the list of favourites
	 * @return number of favourites TV series of the users so far
	 * @throws TSException in case user is not registered or TV series does not exist
	 */
	public int likeTVSeries(String username, String tvSeriesTitle) throws TSException {

		if(users.containsKey(username) && tvseries.containsKey(tvSeriesTitle)) {
			if(users.get(username).getLikeSeries().contains(tvSeriesTitle)) throw new TSException();
			else users.get(username).getLikeSeries().add(tvSeriesTitle);
			return users.get(username).getLikeSeries().size();
		}
		else throw new TSException();
	}
	
	/**
	 * Returns a list of suggested TV series. 
	 * A series is suggested if it is not already in the user list and if it is of the user's favourite genre 
	 * 
	 * @param username name of the user
	 * @throws TSException if user does not exist
	 */
	public List<String> suggestTVSeries(String username) throws TSException {

		List<String> res= null;
		if(users.containsKey(username)) {
			res= tvseries.values().stream().filter(x -> users.get(username).getGenre().equals(x.getGenre()) && !users.get(username).getLikeSeries().contains(x.getTitle()))
					.collect(Collectors.mapping(TVSerie::getTitle, Collectors.toList()));
			if(res.size() == 0) {
				res= new LinkedList<>();
				res.add("");
			}
			return res;
		}
		else throw new TSException();
	}
	
	//R4 

	/**
	 * Add reviews from a user to a tvSeries
	 * 
	 * @param username	username of the user
	 * @param tvSeries		name of the participant
	 * @param score		review score assigned
	 * @return the average score of the series so far from 0 to 10, extremes included
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double addReview(String username, String tvSeries, int score) throws TSException {

		if(users.containsKey(username) && tvseries.keySet().contains(tvSeries) && score>=0.0 && score<=10.0) {
			TVSerie tv = tvseries.get(tvSeries);
			tv.addVote(score);
			users.get(username).getVotes().put(tvSeries,Integer.valueOf(score));
			return tv.getScore();
		}
		else throw new TSException();
	}

	/**
	 * Average rating of tv series in the favourite list of a user
	 * 
	 * @param username	username of the user
	 * @return the average score of the series in the list of favourites of the user
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double averageRating(String username) throws TSException {

		if(users.containsKey(username)) {
			User u = users.get(username);
			double sum=0.0;
			int size= u.getLikeSeries().size();
			for(String t : u.getLikeSeries()) {
				if(u.getVotes().containsKey(t)) sum+=u.getVotes().get(t);
			}
			if(size!=0) return sum/size;
			else return 0.0;
		}
		else throw new TSException();
	}
	
	// R5

	/**
	 * Returns most awaited season of a tv series using format "TVSeriesName seasonNumber", the last season of the best-reviewed TV series who has not come out yet with
	 * respect to the current date passed in input. In case of tie, select using alphabetical order. Date format: dd::mm::yyyy
	 * 
	 * @param currDate	currentDate
	 * @return the most awaited season of TV series who still has to come out
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public String mostAwaitedSeason(String currDate) throws TSException {
		
		String[] date = currDate.split(":");
		int intDate = Integer.parseInt(date[0]) + Integer.parseInt(date[1])*29 + Integer.parseInt(date[2])*365;
		
		for(TVSerie tv : tvseries.values().stream().sorted(Comparator.comparing(TVSerie::getScore).reversed()).filter(x -> x.getSeasons()!=null).toList()) {
			Optional<Season> s= tv.getSeasons().stream().filter(x -> x.getReleaseDate()>intDate).findFirst();
			if(s.isPresent()) {
				return tv.getTitle() + " " + String.valueOf(tv.getSeasons().indexOf(s.get())+1);
			}
		}
		return "";
	}
	
	/**
	 * Computes the best actors working in tv series of a transmission service passed
	 * in input. The best actors have worked only in tv series of that transmission service
	 * with average rating higher than 8.
	 * @param transmissionService	transmission service to consider
	 * @return the best actors' names as "name surname" list
	 * @throws TSException	in case of transmission service not in the DB
	 */
	public List<String> bestActors(String transmissionService) throws TSException {
		
		List<String> res= new LinkedList<>();
		if(trasmissionServices.contains(transmissionService)) {
			List<TVSerie> list= tvseries.values().stream().filter(x -> x.gettService().equals(transmissionService)).toList();
			for(TVSerie tv : list) {
				tv.getActors().stream().filter(x -> actors.get(x).isBest()).forEach(x -> res.add(x));
			}
		}
		else throw new TSException();
		return res;
	}

	
}
