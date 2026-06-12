package it.polito.project;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ReviewServer {
	
	private Map<String,List<Review>> groups= new HashMap<>();

	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {
		for(String g : groups){
			this.groups.put(g,new LinkedList<Review>());
		}
	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		return groups.keySet();
	}
	
	
	/**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */
	
	public String addReview(String title, String topic, String group) throws ReviewException {
		if(groups.keySet().contains(group)){
			List<Review> l= groups.get(group);
			Review r= new Review(title,topic,group);
			l.add(r);
			return String.valueOf(r.getCode());
		}
		else throw new ReviewException();
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		return groups.get(group).stream().map(x-> String.valueOf(x.getCode())).collect(Collectors.toList());
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()) return or.get().getTitle();
		}
		return " ";
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()) return or.get().getTopic();
		}
		return " ";
	}

	// R2
		
	/**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */
	
	public double getNumStartOrEnd(String date,String startEnd){
		String[]fields= date.split(":");
		double res= Integer.parseInt(fields[0])*24 + Integer.parseInt(fields[0])*30*24 + Integer.parseInt(fields[0])*356*24;
		String[]other= startEnd.split(":");
		res+= Integer.parseInt(other[0]) + Integer.parseInt(other[0])/60;
		return res;
	}
	
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review review=or.get();
				Option opt= new Option(reviewId, date,start,end);
				for(Option o : review.getOptions()){
					if(o.OptionCollide(opt)) throw new ReviewException();
				}
				review.getOptions().add(opt);
				return opt.getDurata();
			}
		}
		throw new ReviewException();
	}

	/**
	 * retrieves the time slots available for a given review.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param reviewId		id of the review
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review rev= or.get();
				return rev.getOptions().stream().collect(Collectors.groupingBy(Option::getDate, Collectors.mapping(Option::toStringStartEnd, Collectors.toList())));
			}
		}
		return null;
	}

	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review rev= or.get();
				rev.setOpenPoll(true);
			}
		}
	}


	/**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review rev= or.get();
				if(rev.isOpenPoll()){
					String[]startEnd= slot.split("-");
					Option option=new Option(reviewId,date,startEnd[0],startEnd[1]);
					if(rev.getOptionFromTime(date, startEnd[0], startEnd[1]).isEmpty()) throw new ReviewException(); 
					option.setAssociatedEmail(email);
					return rev.addPreference(option,new Student(email,name,surname));
				}
				else throw new ReviewException();
			}
		}
		throw new ReviewException();
	}

	/**
	 * retrieves the list of the preferences expressed for a review.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId review id
	 * @return list of preferences for the review
	 */
	public Collection<String> listPreferences(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review rev= or.get();
				return rev.getPreferences().values().stream().map(Option::toString).collect(Collectors.toList());
			}
		}
		return null;
	}

	/**
	 * close the poll associated to a review and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId	id of the review
	 */
	public Collection<String> closePoll(String reviewId) {
		List<String> res= new LinkedList<>();
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){
				Review rev= or.get();
				rev.setOpenPoll(false);
				res.add(rev.getPreferences().values().stream().filter(Option::getConsider).max(Comparator.comparingInt(x->x.getPrefInt())).get().toStringWithNumPref()); return res;
			}
		}
		return null;
	}

	
	/**
	 * returns the preference count for each slot of a review
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * @param reviewId	the id of the review
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		for(List<Review> r :groups.values()){
			Optional<Review> or=r.stream().filter(x->x.getStringCode().equals(reviewId)).findAny();
			if(or.isPresent()){ Review rev= or.get(); return rev.getOptions().stream().collect(Collectors.groupingBy(Option::getDate,Collectors.filtering(Option::getConsider, Collectors.mapping(Option::toStringJustStEnWithNumPref, Collectors.toList())))); }
		} return null;                                                         
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		List<Review> glb= new LinkedList<>(); Map<String,Integer> ret= new HashMap<>();
		for(List<Review> r : groups.values()) {glb.addAll(r);} for(Review r : glb) {ret.put(r.getStringCode(), r.SumPrefInteger());} return ret; }
}
