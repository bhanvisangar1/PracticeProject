package com.example.mohsharma.practiceproject.service;

import com.example.mohsharma.practiceproject.model.SearchResponse;
import com.example.mohsharma.practiceproject.SearchParams;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchService {

	private static final String END_POINT = "https://api.airbnb.com";

	private static SearchApi getApi() {

		OkHttpClient.Builder client = new OkHttpClient.Builder();

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);
		client.addInterceptor(logging);

		return new Retrofit.Builder()
			.baseUrl(END_POINT)
			.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.client(client.build())
			.build().create(SearchApi.class);
	}

	public static void doSearch(SearchParams searchParams, Observer<SearchResponse> observer) {

		String latitude = null;
		String longitude = null;
		if (searchParams.getLatLong() != null) {
			latitude = searchParams.getLatLong().latitude;
			longitude = searchParams.getLatLong().longitude;
		}
		Observable<SearchResponse> serviceResponse = getApi()
			.getSearchResults(searchParams.getLocation(), latitude,
				longitude, searchParams.getGuests());
		serviceResponse
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeOn(Schedulers.io())
			.subscribe(observer);
	}
}
