package it.polito.project;

public class Option {
	
	private String reviewId;
	private String date;
	private String start;
	private String end;
	private String associatedEmail;
	private boolean consider=false;
	private String howManyPreferences;
	public Option(String reviewId, String date, String start, String end) {
		super();
		this.reviewId = reviewId;
		this.date = date;
		this.start = start;
		this.end = end;
	}
	
	public String toStringStartEnd(){
		return start + "-" + end;
	}
	
	
	public String getHowManyPreferences() {
		return howManyPreferences;
	}

	public boolean getConsider() {return consider;}public void setConsider(){consider=true;} public int getPrefInt(){return Integer.parseInt(howManyPreferences);}



	public void setHowManyPreferences(String howManyPreferences) {
		this.howManyPreferences = howManyPreferences;
	}




	public String getAssociatedEmail() {
		return associatedEmail;
	}


	public void setAssociatedEmail(String associatedEmail) {
		this.associatedEmail = associatedEmail;
	}


	public boolean OptionCollide(Option o){
		return (getNumStartOrEnd(o.getDate(),o.getStart())>=getNumStartOrEnd(getDate(),getStart()) && getNumStartOrEnd(o.getDate(),o.getStart())<getNumStartOrEnd(getDate(),getEnd()))||(getNumStartOrEnd(o.getDate(),o.getStart())<getNumStartOrEnd(getDate(),getStart()) && getNumStartOrEnd(o.getDate(),o.getEnd())>getNumStartOrEnd(getDate(),getStart()));
	}
	
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	@Override
	public String toString() {
		if(associatedEmail!=null) return date + "T" + start + "-" + end + "=" + associatedEmail;
		else return date + "T" + start + "-" + end;
	}
	public String toStringWithNumPref() {
		return date + "T" + start + "-" + end + "=" + howManyPreferences;
	}
	public String toStringJustStEnWithNumPref() { return start + "-" + end + "=" + howManyPreferences;}
	public double getNumStartOrEnd(String date,String startEnd){
		String[]fields= date.split("-");
		double res= Integer.parseInt(fields[2])*24 + Integer.parseInt(fields[1])*30*24 + Integer.parseInt(fields[0])*356*24;
		String[]other= startEnd.split(":");
		res+= Integer.parseInt(other[0]) + Integer.parseInt(other[1])/60.00;
		return res;
	}
	public double getDurata(){
		return getNumStartOrEnd(getDate(),getEnd())- getNumStartOrEnd(getDate(),getStart());
	}

}
