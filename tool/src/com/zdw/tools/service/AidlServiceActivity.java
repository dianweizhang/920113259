package com.zdw.tools.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.zdw.tools.R;

public class AidlServiceActivity extends Activity{

	private MyAidl_Service mServiceBind;
	private boolean isBand = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aidl_main);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isBand) {
			isBand = false;
			unbindService(mConnection);
		}
	}

	public void StartService(View view) {
		isBand = true;
		Intent serviceIntent = new Intent(this,AidlService.class);
		bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void sayHello(View view) {
		if(mServiceBind != null) {
			try {
				//acitvity 调用 service 方法
				mServiceBind.invokCallBack();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			mServiceBind = MyAidl_Service.Stub.asInterface(arg1);
			if(mServiceBind != null) {
				try {
					//acitvity 调用 service 方法
					mServiceBind.registerTestCall(mCallBack);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	MyAidl_Activity.Stub mCallBack = new MyAidl_Activity.Stub() {
		@Override
		public void performAction() throws RemoteException {
			Toast.makeText(AidlServiceActivity.this, "Hello", Toast.LENGTH_SHORT).show();
		}
	}; 
}
