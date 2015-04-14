package com.zdw.tools.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BindService extends Service{
	
	private MyBind mMyBind = new MyBind();;
	
	//bind 只能被绑定一次
	@Override
	public IBinder onBind(Intent arg0) {
		return mMyBind;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	//http://mobile.51cto.com/android-315622.htm
	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}



	//bind 方式不调用 onStartCommand
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}



	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}



	public class MyBind extends Binder {
		public void sayHello() {
			Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
		}
	}

}
