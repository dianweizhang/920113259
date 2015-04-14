package com.zdw.tools.widget.calendar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

import com.zdw.tools.util.Utils;

public class Day implements Parcelable {
	private Rect mBound = null;
	private int year;
	private int month;
	private int mDayOfMonth = 1;	// from 1 to 31
	private Paint mPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG
            |Paint.ANTI_ALIAS_FLAG);
	private Paint mBGPaint = new Paint();
	public int dx, dy;
	protected boolean isDraw = true;
	private Bitmap mBitmap;

	public Day(Bitmap bitmap, int year, int month, int dayOfMon, Rect rect, float textSize, boolean bold) {
		mBitmap = bitmap;
		this.year = year;
		this.month = month;
		mDayOfMonth = dayOfMon;
		mBound = rect;
		mPaint.setTextSize(textSize);
		mPaint.setColor(0xff444444);
		if(bold) mPaint.setFakeBoldText(true);
		
		dx = (int) mPaint.measureText(String.valueOf(mDayOfMonth)) / 2;
		dy = (int) (-mPaint.ascent() + mPaint.descent()) / 2;
		
		mBGPaint.setColor(Color.WHITE);
	}
	
	public Day(Bitmap bitmap, int year, int month, int dayOfMon, Rect rect, float textSize) {
		this(bitmap,year, month, dayOfMon, rect, textSize, false);
	}
	
	public Day(){}
	
	public void setBGColor(int color) {
		mBGPaint.setColor(color);
	}
	
	public void draw(Canvas canvas) {
		if(!isDraw) return;
		canvas.drawRect(mBound.left, mBound.top, mBound.right, mBound.bottom, mBGPaint);
		canvas.drawText(String.valueOf(mDayOfMonth), mBound.centerX() - dx, mBound.centerY() + dy, mPaint);
		
		if(mBitmap != null) {
			int left = mBound.left + (mBound.width() - mBitmap.getWidth())/2;
			int top = mBound.centerY() + dy + 12;
			canvas.drawBitmap(mBitmap, left, top, mPaint);
		}
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDayOfMonth() {
		return mDayOfMonth;
	}
	
	public boolean hitTest(int x, int y) {
		return mBound.contains(x, y); 
	}
	
	public Rect getBound() {
		return mBound;
	}
	
	public String toString() {
		return String.valueOf(mDayOfMonth)+"("+mBound.toString()+")";
	}
	
	public Paint getTextPaint() {
		return mPaint;
	}

	public void setTextPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public void setToday(Bitmap bitmap) {
		mPaint.setColor(0xff00aea9);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
//		mTodayBitmap = bitmap;
	}
	
	/**
	 * 
	 * @return yyyymmdd
	 */
	public String getYyyymmdd() {
		return year + Utils.format(month) + Utils.format(mDayOfMonth);
	}
	
	 public static final Parcelable.Creator<Day> CREATOR
     		= new Parcelable.Creator<Day>() {
         public Day createFromParcel(Parcel p) {
             return new Day(p);
         }

         public Day[] newArray(int size) {
             return new Day[size];
         }
     };
     
	public Day(Parcel p) {
		year = p.readInt();
		month = p.readInt();
		mDayOfMonth = p.readInt();
     }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int flags) {
		p.writeInt(year);
		p.writeInt(month);
		p.writeInt(mDayOfMonth);
	}
}
