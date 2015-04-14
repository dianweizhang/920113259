package com.zdw.tools.util;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableData implements Parcelable {
	public String id = "";

	public ParcelableData(){
	}
	
	public static final Parcelable.Creator<ParcelableData> CREATOR
		    = new Parcelable.Creator<ParcelableData>() {
		public ParcelableData createFromParcel(Parcel in) {
		    return new ParcelableData(in);
		}
		public ParcelableData[] newArray(int size) {
		    return new ParcelableData[size];
		}
	};
		
	private ParcelableData(Parcel in) {
		id = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
	}
}
