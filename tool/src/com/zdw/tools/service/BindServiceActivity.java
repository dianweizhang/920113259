package com.zdw.tools.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.zdw.tools.R;
import com.zdw.tools.service.BindService.MyBind;

public class BindServiceActivity extends Activity{

	private MyBind mMyBind;
	private boolean isBind = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_main);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isBind) {
			isBind = false;
			unbindService(mConnection);
		}
	}



	public void bindService(View view) {
		isBind = true;
		Intent bindService = new Intent("com.zdw.tools.bindService.BIND");
		bindService(bindService,mConnection,Context.BIND_AUTO_CREATE);
	}
	
	public void sayHello(View view) {
		if(mMyBind != null) {
			mMyBind.sayHello();
		}
	}
	
	ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			mMyBind = (MyBind) arg1;
		}
	};
}
