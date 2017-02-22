package com.example.mohsharma.practiceproject;

import java.util.Currency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.mohsharma.practiceproject.model.SearchResponse;
import rx.functions.Action1;


public class SearchActivity extends AppCompatActivity {
	private static final String TAG = SearchActivity.class.getSimpleName();

	@BindView(R.id.layout_container)
	FrameLayout layoutContainer;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.container);
		ButterKnife.bind(this);
		SearchFragment searchFragment = new SearchFragment();
		searchFragment.responseSubject.subscribe(new Action1<SearchResponse>() {
			@Override
			public void call(SearchResponse searchResponse) {
				String currencyCode = Currency.getInstance("USD").getSymbol();
				Log.v(TAG, "On Next called " + searchResponse.resultList.size() + " name " + currencyCode);
				ResultListFragment resultListFragment = new ResultListFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, resultListFragment).addToBackStack(null).commit();
				resultListFragment.searchResponseBehaviorSubject.onNext(searchResponse);

			}
		});
		getSupportFragmentManager().beginTransaction().add(R.id.layout_container, searchFragment).commit();

	}



}
