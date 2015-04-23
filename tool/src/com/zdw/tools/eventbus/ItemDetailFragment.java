package com.zdw.tools.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdw.tools.R;

import de.greenrobot.event.EventBus;

public class ItemDetailFragment extends Fragment
{

	private TextView tvDetail;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// register
		EventBus.getDefault().register(this);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// Unregister
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(Item item)
	{
		if (item != null)
			tvDetail.setText(item.content);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.eventbus_fragment_item_detail,
				container, false);
		tvDetail = (TextView) rootView.findViewById(R.id.item_detail);
		return rootView;
	}
}
