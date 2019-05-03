package com.filteredmatches.dto;

public class FilterDTO {
	
	String hasPhoto;
	String distanceLimit;
	String lowerLimitCompatibility;
	String upperLimitCompatibility;
	String lowerLimitAge;
	String upperLimitAge;
	String isFavourite;

	String lowerLimitHeight;
	String upperLimitHeight;
	String hasContactsExchanged;
	private String religion;
	
	public String getIsFavourite() {
		return isFavourite;
	}


	public void setIsFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}


	public String getHasContactsExchanged() {
		return hasContactsExchanged;
	}


	public void setHasContactsExchanged(String hasContactsExchanged) {
		this.hasContactsExchanged = hasContactsExchanged;
	}



	public String getLowerLimitAge() {
		return lowerLimitAge;
	}


	public void setLowerLimitAge(String lowerLimitAge) {
		this.lowerLimitAge = lowerLimitAge;
	}


	public String getUpperLimitAge() {
		return upperLimitAge;
	}


	public void setUpperLimitAge(String upperLimitAge) {
		this.upperLimitAge = upperLimitAge;
	}


	public String getLowerLimitHeight() {
		return lowerLimitHeight;
	}


	public void setLowerLimitHeight(String lowerLimitHeight) {
		this.lowerLimitHeight = lowerLimitHeight;
	}


	public String getUpperLimitHeight() {
		return upperLimitHeight;
	}


	public void setUpperLimitHeight(String upperLimitHeight) {
		this.upperLimitHeight = upperLimitHeight;
	}


	public String getUpperLimitCompatibility() {
		return upperLimitCompatibility;
	}


	public void setUpperLimitCompatibility(String upperLimitCompatibility) {
		this.upperLimitCompatibility = upperLimitCompatibility;
	}


	public String getLowerLimitCompatibility() {
		return lowerLimitCompatibility;
	}


	public void setLowerLimitCompatibility(String lowerLimitCompatibility) {
		this.lowerLimitCompatibility = lowerLimitCompatibility;
	}


	public String getDistanceLimit() {
		return distanceLimit;
	}


	public void setDistanceLimit(String distanceLimit) {
		this.distanceLimit = distanceLimit;
	}


	public String getHasPhoto() {
		return hasPhoto;
	}
	

	public void setHasPhoto(String hasPhoto) {
		this.hasPhoto = hasPhoto;
	}


	public void setReligion(String religion) {
		this.religion= religion;
		
	}


	public String getReligion() {
		return religion;
	}
	

}
