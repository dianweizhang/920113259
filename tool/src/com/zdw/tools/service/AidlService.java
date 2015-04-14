package com.zdw.tools.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AidlService extends Service{
	private MyAidl_Activity mCallBack;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return myBind;
	}

	private final MyAidl_Service.Stub myBind = new MyAidl_Service.Stub() {
		
		@Override
		public void registerTestCall(MyAidl_Activity cb) throws RemoteException {
			mCallBack = cb;
		}
		
		@Override
		public void invokCallBack() throws RemoteException {
			mCallBack.performAction();
		}
	};
}
