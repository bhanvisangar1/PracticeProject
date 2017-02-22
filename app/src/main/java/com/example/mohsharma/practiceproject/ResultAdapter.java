package com.example.mohsharma.practiceproject;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.mohsharma.practiceproject.model.SearchResponse;
import com.squareup.picasso.Picasso;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultListViewHolder> {

	List<SearchResponse.Result> resultList = new ArrayList<>();

	public void setResultList(List<SearchResponse.Result> resultList) {
		this.resultList.clear();
		this.resultList = resultList;
		notifyDataSetChanged();
	}

	@Override
	public ResultListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row, parent, false);
		return new ResultListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ResultListViewHolder holder, int position) {
		holder.textView.setText(resultList.get(position).listing.name);
		Picasso.with(holder.imageView.getContext()).load(resultList.get(position).listing.url).into(holder.imageView);
		holder.guestCountTextView.setText(holder.imageView.getContext().getString(R.string.max_guest) + resultList
			.get(position).listing.maxGuestCount);
		holder.rateTextView.setText(resultList.get(position).pricingInfo.getNightlyRate());
	}

	@Override
	public int getItemCount() {
		return resultList.size();
	}

	public class ResultListViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.property_name)
		TextView textView;
		@BindView(R.id.property_image)
		ImageView imageView;
		@BindView(R.id.max_guest_count)
		TextView guestCountTextView;
		@BindView(R.id.rate_textView)
		TextView rateTextView;

		public ResultListViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
