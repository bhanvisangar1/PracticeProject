package com.example.mohsharma.practiceproject;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ResultItemDecoration extends RecyclerView.ItemDecoration {

	int margin = 0;

	public ResultItemDecoration(Context context) {
		margin = context.getResources().getDimensionPixelOffset(R.dimen.card_margin);
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
		boolean isFirstItem = parent.getChildAdapterPosition(view) == 0;
		layoutParams.leftMargin = margin;
		if (isFirstItem) {
			layoutParams.topMargin = margin;
		}
		layoutParams.rightMargin = margin;
		layoutParams.bottomMargin = margin;
		view.setLayoutParams(layoutParams);
		super.getItemOffsets(outRect, view, parent, state);
	}
}
