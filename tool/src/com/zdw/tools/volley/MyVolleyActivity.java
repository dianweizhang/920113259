package com.zdw.tools.volley;

import android.app.ListActivity;
import android.os.Bundle;

public class MyVolleyActivity extends ListActivity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ImageRequestAdapter(this, UrlBean.urls, 0));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		VolleyTool.getInstance(this).getmRequestQueue().cancelAll(MyVolleyActivity.class.getSimpleName());
	}
}
