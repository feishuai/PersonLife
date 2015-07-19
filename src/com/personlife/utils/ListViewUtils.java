package com.personlife.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ListViewUtils {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter(); 
        if (listAdapter == null) {
            return;
        }       
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if(listItem != null){
                listItem.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                listItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) 
                + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }
}
