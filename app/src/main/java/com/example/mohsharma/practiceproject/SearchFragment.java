package com.example.mohsharma.practiceproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.mohsharma.practiceproject.model.SearchResponse;
import com.example.mohsharma.practiceproject.service.SearchService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import rx.Observer;
import rx.subjects.BehaviorSubject;


public class SearchFragment extends Fragment
	implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	private static final String TAG = SearchFragment.class.getSimpleName();

	@BindView(R.id.location_edit_text)
	EditText locationName;
	@BindView(R.id.location_input_layout)
	TextInputLayout locationNameLayout;

	@BindView(R.id.current_location_button)
	Button currentLocationButton;

	@BindView(R.id.search_button)
	Button searchButton;

	@BindView(R.id.guest_edit_text)
	EditText guestEditText;

	@BindView(R.id.guest_input_layout)
	TextInputLayout guestLayout;

	ProgressDialog progressDialog;

	GoogleApiClient googleApiClient;

	public BehaviorSubject<SearchResponse> responseSubject = BehaviorSubject.create();
	SearchParams.LatLong latLong = new SearchParams.LatLong();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_layout, container, false);
		ButterKnife.bind(this, view);
		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		progressDialog = new ProgressDialog(getActivity());
		currentLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PackageManager.PERMISSION_GRANTED != ContextCompat
					.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
					ActivityCompat
						.requestPermissions(getActivity(), new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
							1);
				}
				else {
					getCurrentLocation();

				}
			}
		});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
		@NonNull int[] grantResults) {
		if (requestCode == 1) {
			if (grantResults != null && grantResults.length > 0) {
				int permissionGranted = grantResults[0];
				if (PackageManager.PERMISSION_GRANTED == permissionGranted) {
					Log.v(TAG, "use current location from result");
					getCurrentLocation();
				}
			}
		}
	}

	@OnClick(R.id.search_button)
	public void onClick(View view) {
		Log.v(TAG, "Search Button clicked");
		String location = locationName.getText().toString();
		String guestCount = guestEditText.getText().toString();
		int guest = 1;
		if (guestCount != null && !guestCount.isEmpty()) {
			guest = Integer.parseInt(guestCount);
		}
		progressDialog.setMessage(getString(R.string.loading_please));
		progressDialog.setCancelable(false);
		progressDialog.show();
		SearchParams.SearchParamsBuilder searchParamsBuilder = new SearchParams.SearchParamsBuilder().setGuests(guest);
		if(locationName.getText().toString().equalsIgnoreCase(getString(R.string.current_location))) {
			searchParamsBuilder.setLatLong(latLong);

		}else {
			searchParamsBuilder = searchParamsBuilder.setLocation(location);
		}

		SearchService.doSearch(searchParamsBuilder.build(), searchResponseObserver);
	}

	Observer<SearchResponse> searchResponseObserver = new Observer<SearchResponse>() {
		@Override
		public void onCompleted() {
			Log.v(TAG, "On completed called");
		}

		@Override
		public void onError(Throwable e) {
			progressDialog.dismiss();
			Log.v(TAG, "On Error called " + e.getMessage());

		}

		@Override
		public void onNext(SearchResponse searchResponse) {
			progressDialog.dismiss();
			responseSubject.onNext(searchResponse);
		}
	};

	public void getCurrentLocation() {
		googleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
			.addApi(LocationServices.API).build();
		googleApiClient.connect();

	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
		locationName.setText(R.string.current_location);
		latLong.latitude = Double.toString(location.getLatitude());
		latLong.longitude = Double.toString(location.getLongitude());
		Log.v(TAG, "Curent location is "+location.getLatitude()+ location.getLongitude());
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

	}
}
