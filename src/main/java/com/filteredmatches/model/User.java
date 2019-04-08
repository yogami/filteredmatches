package com.filteredmatches.model;

public class User {

	private int userId;

	private String display_name;
	private Short age;
	private String job_title;
	private Short height_in_cm;
	private City city;
	private String main_photo;
	private Float compatibility_score;
	private Short contacts_exchanged;
	private Boolean favourite;
	private String religion;
	private Float distanceInKms;
	private Integer compatibilityPercentage;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDistanceInKms() {
		return distanceInKms.intValue();
	}
	public void setDistanceInKms(Float distanceInKms) {
		this.distanceInKms = distanceInKms;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public Short getAge() {
		return age;
	}
	public void setAge(Short age) {
		this.age = age;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public Short getHeight_in_cm() {
		return height_in_cm;
	}
	public void setHeight_in_cm(Short height_in_cm) {
		this.height_in_cm = height_in_cm;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getMain_photo() {
		return main_photo;
	}
	public void setMain_photo(String main_photo) {
		this.main_photo = main_photo;
	}
	public Float getCompatibility_score() {
		return compatibility_score;
	}
	
	public Integer getCompatibilityPercentage() {
		return Float.valueOf(compatibility_score*100).intValue();
	}
	public void setCompatibility_score(Float compatibility_score) {
		this.compatibility_score = compatibility_score;
	}
	public Short getContacts_exchanged() {
		return contacts_exchanged;
	}
	public void setContacts_exchanged(Short contacts_exchanged) {
		this.contacts_exchanged = contacts_exchanged;
	}
	public Boolean getFavourite() {
		return favourite;
	}
	public void setFavourite(Boolean favourite) {
		this.favourite = favourite;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}

}
