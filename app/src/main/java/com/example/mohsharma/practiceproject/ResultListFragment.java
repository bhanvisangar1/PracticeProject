package com.example.mohsharma.practiceproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.mohsharma.practiceproject.model.SearchResponse;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;


public class ResultListFragment extends Fragment {
	private static final String TAG = ResultListFragment.class.getSimpleName();

	BehaviorSubject<SearchResponse> searchResponseBehaviorSubject = BehaviorSubject.create();

	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	ResultAdapter resultAdapter;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.result_fragment, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		resultAdapter = new ResultAdapter();
		recyclerView.addItemDecoration(new ResultItemDecoration(getActivity()));
		recyclerView.setAdapter(resultAdapter);
		searchResponseBehaviorSubject.subscribe(new Action1<SearchResponse>() {
			@Override
			public void call(SearchResponse searchResponse) {
				resultAdapter.setResultList(searchResponse.resultList);

				Log.v(TAG, "got the result show me");
			}
		});

	}
}
