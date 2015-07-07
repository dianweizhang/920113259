package com.zdw.tools.myview;

import android.app.Activity;
import android.os.Bundle;

import com.zdw.tools.R;

public class DatePickerActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setTheme(R.style.NumberPicker);
		setContentView(R.layout.activity_datepicker);
	}

}
