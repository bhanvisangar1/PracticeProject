package com.example.mohsharma.practiceproject.service;

import com.example.mohsharma.practiceproject.model.SearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchApi {
	// https://api.airbnb.com/v2/search_results
	@GET("/v2/search_results?client_id=3092nxybyb0otqw18e8nh5nty&_limit=50&locale=en_US")
	Observable<SearchResponse> getSearchResults(
		@Query("location") String location,
		@Query("user_lat") String latitude,
		@Query("user_lng") String longitude,
		@Query("guests") int guests
	);
}
