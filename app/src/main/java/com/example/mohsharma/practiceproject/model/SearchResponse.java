package com.example.mohsharma.practiceproject.model;

import java.util.Currency;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class SearchResponse {

	@SerializedName("metadata")
	public MetaData metadata;
	@SerializedName("search_results")
	public List<Result> resultList;

	public class MetaData {
		public Location location;
		@SerializedName("listings_count")
		public String listingCount;
	}

	public class Location {
		@SerializedName("display_location")
		public String locationName;
	}

	public class Result {
		public Listing listing;
		@SerializedName("pricing_quote")
		public PricingInfo pricingInfo;
	}

	public class Listing {
		public String id;
		public String name;
		@SerializedName("xl_picture_url")
		public String url;

		@SerializedName("person_capacity")
		public String maxGuestCount;


	}

	public class PricingInfo {
		@SerializedName("nightly_price")
		String nightlyPrice;
		@SerializedName("listing_currency")
		String currency;
		public String getNightlyRate(){
			return Currency.getInstance(currency).getSymbol()+ nightlyPrice;
		}

	}
}
