package it.polito.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Review {
	private String title;
	private String topic;
	private String group;
	private int code;
	private List<Option> options= new LinkedList<>();
	private static int SequentialCode=0;
	private boolean openPoll;
	private Map<Student,Option> preferences= new HashMap<>();
	
	public int addPreference(Option o,Student s){
		int res=1;
		for(Option opt : preferences.values()){
			if(opt.getDate().equals(o.getDate()) && opt.getStart().equals(o.getStart()) && opt.getEnd().equals(o.getEnd())) res++;
		}
		o.setHowManyPreferences(String.valueOf(res)); for(Option opt : preferences.values()){ if(opt.getDate().equals(o.getDate()) && opt.getStart().equals(o.getStart()) && opt.getEnd().equals(o.getEnd())) opt.setHowManyPreferences(String.valueOf(res));}
		if(res==1) {o.setConsider();} preferences.put(s,o); options.add(o);
		return res;
	}
	public Optional<Option> getOptionFromTime(String date, String start, String end) {for (Option o : options) {if(o.getDate().equals(date)&&o.getStart().equals(start)&&o.getEnd().equals(end)) {return Optional.of(o);}}return Optional.empty();}
	
	public Map<Student,Option> getPreferences() {
		return preferences;
	}



	public void addOption(Option o){
		options.add(o);
	}
	
	
	public boolean isOpenPoll() {
		return openPoll;
	}


	public void setOpenPoll(boolean openPoll) {
		this.openPoll = openPoll;
	}


	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public Review(String title, String topic, String group) {
		super();
		this.title = title;
		this.topic = topic;
		this.group = group;
		this.code= SequentialCode;
		SequentialCode+=1;
	}


	public static int getSequentialCode() {
		return SequentialCode;
	}

	public Integer SumPrefInteger() { int r=0; for(Option o: preferences.values()) {if(o.getConsider()) r+=o.getPrefInt();} return r;}

	public static void setSequentialCode(int sequentialCode) {
		SequentialCode = sequentialCode;
	}


	public String getStringCode(){
		return String.valueOf(code);
	}
	
	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String toString() {
		return "Review [title=" + title + ", topic=" + topic + ", group="
				+ group + "]";
	}
	
	
}
