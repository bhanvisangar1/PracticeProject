package com.example.mohsharma.practiceproject;


public class SearchParams {

	private String location;
	private LatLong latLong;
	private int guests;

	public int getGuests() {
		return guests;
	}

	public String getLocation() {
		return location;
	}

	public LatLong getLatLong() {
		return latLong;
	}

	private SearchParams(String location, LatLong latLong, int guests) {
		this.location = location;
		this.latLong = latLong;
		this.guests = guests;
	}

	public static class SearchParamsBuilder {
		private String location;
		private LatLong latLong;
		private int guests = 10;

		public SearchParamsBuilder setLocation(String loc) {
			this.location = loc;
			return this;
		}

		public SearchParamsBuilder setGuests(int guests) {
			this.guests = guests;
			return this;
		}

		public SearchParamsBuilder setLatLong(LatLong latLong) {
			this.latLong = latLong;
			return this;
		}

		public SearchParams build() {
			if(location == null && latLong == null) throw new IllegalArgumentException();
			if(guests == 0) guests = 1;
			return new SearchParams(location,latLong, guests);
		}
	}

	public static class LatLong {
		public String latitude;
		public String  longitude;
	}
}


