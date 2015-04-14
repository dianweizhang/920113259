package com.zdw.tools.widget.calendar;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.View;
/**
 * 
 * @author yuanzhi.cjy
 *
 */
public class CalendarView extends View{
	public static final int DEFAULT_BOARD_SIZE = 100;
	private static float CELL_TEXT_SIZE = 20;
	
	private int mCellWidth;
	private int mCellHeight;
	
	public static final int CURRENT_MOUNT = 0;
    public static final int NEXT_MOUNT = 1;
    public static final int PREVIOUS_MOUNT = -1;
	//当时时间
	private Calendar mRightNow = null;
	//选中的cell
	private Day mTouchedCell = null;
	private Day mTodayCell = null;
	private Day mTomomowCell = null;
	//全部cell
	private Day[][] mCells = new Day[6][7];
	//月份改变listener
	private OnMonthChangeListener monthChangeListener;
	MonthDisplayHelper mHelper;
	//grid背景
	private Paint mBackgroundColor;
	//当天背景
//	private Paint mBackgroundColorToday;
//	//选中背景
//	private Paint mBackgroundColorTouched;
	
	private Resources mResources;
	public CalendarView(Context context) {
		this(context, null);
		mResources = context.getResources();
	}
	
	public CalendarView(Context context, AttributeSet attrs){
		super(context, attrs);
		mResources = context.getResources();
		initCalendarView();
	}
	
	private void initCalendarView() {
		mRightNow = Calendar.getInstance();
		mHelper = new MonthDisplayHelper(
					mRightNow.get(Calendar.YEAR),
					mRightNow.get(Calendar.MONTH),
					mRightNow.getFirstDayOfWeek()
				);
		
		mBackgroundColor = new Paint();
//		mBackgroundColorToday = new Paint();
//		mBackgroundColorTouched = new Paint();
		
		mBackgroundColor.setColor(Color.rgb(35, 35, 35));
//		mBackgroundColorToday.setColor(Color.RED);
//		mBackgroundColorToday.setAlpha(100);
//		mBackgroundColorTouched.setColor(mResources.getColor(R.color.week_select));
//		mBackgroundColorTouched.setAlpha(100);
	}
	
	private void initCells() {
		class _calendar {
			public int year;
			public int month;
	    	public int day;
	    	public int whichMonth;  // -1 为上�?1为下�?0为当前月
	    	public _calendar(int y, int m, int d, int b) {
	    		year = y;
	    		month = m;
	    		day = d;
	    		whichMonth = b;
	    	}
	    	public _calendar(int y, int m, int d) { 
	    		this(y, m, d, PREVIOUS_MOUNT);
	    	}
	    };
	    //保存上个月�?当前月�?下个月日历数�?
	    _calendar tmp[][] = new _calendar[6][7];
	    
	    for(int i=0; i<tmp.length; i++) {
	    	int n[] = mHelper.getDigitsForRow(i);
	    	for(int d=0; d<n.length; d++) {
	    		if(mHelper.isWithinCurrentMonth(i,d))
	    			tmp[i][d] = new _calendar(mHelper.getYear(), mHelper.getMonth()+1, n[d], CURRENT_MOUNT);
	    		else if(i == 0) {
	    			tmp[i][d] = new _calendar(mHelper.getYear(), mHelper.getMonth(), n[d]);
	    		} else {
	    			tmp[i][d] = new _calendar(mHelper.getYear(), mHelper.getMonth()+2, n[d], NEXT_MOUNT);
	    		}
	    		
	    	}
	    }
	    
	    Calendar today = Calendar.getInstance();
	    int thisDay = 0;
	    if(mHelper.getYear()==today.get(Calendar.YEAR) && mHelper.getMonth()==today.get(Calendar.MONTH)) {
	    	thisDay = today.get(Calendar.DAY_OF_MONTH);
	    }
	    mTouchedCell = null;
	    int today_w = 0;
	    int today_d = 0;
	    // build cells
		Rect Bound = new Rect(getPaddingLeft(), getPaddingTop(), mCellWidth+getPaddingLeft(), mCellHeight+getPaddingTop());
		for(int week=0; week<mCells.length; week++) {
			for(int day=0; day<mCells[week].length; day++) {
				if(tmp[week][day].whichMonth == CURRENT_MOUNT) { // 当前月设置cell
					if(day==0 || day==6 ) {
						mCells[week][day] = new WeekEndDay(null, tmp[week][day].year, tmp[week][day].month, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
					}else {
						mCells[week][day] = new Day(null, tmp[week][day].year, tmp[week][day].month, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
					}
				} else if(tmp[week][day].whichMonth == PREVIOUS_MOUNT) {  // 上月为gray
					mCells[week][day] = new LastMonthDay(tmp[week][day].year, tmp[week][day].month, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
				} else { // 下月为LTGray
					mCells[week][day] = new NextMonthDay(tmp[week][day].year, tmp[week][day].month, tmp[week][day].day, new Rect(Bound), CELL_TEXT_SIZE);
				}
				Bound.offset(mCellWidth, 0); // move to next column 
				// get today
				if(tmp[week][day].day==thisDay && tmp[week][day].whichMonth == 0) {
					mTodayCell = mCells[week][day];
					today_w = week;
					today_d = day;
					mCells[week][day].setToday(null);
				}
			}
			Bound.offset(0, mCellHeight); // move to next row and first column
			Bound.left = getPaddingLeft();
			Bound.right = getPaddingLeft()+mCellWidth;
		}
		if(++today_d >= mCells[today_w].length) {
			today_w++;
			today_d = 0;
		}
		mTomomowCell = mCells[today_w][today_d];
	}
	
	public Day getTodayCell() {
		return mTodayCell;
	}

	public void setTodayCell(Day mTodayCell) {
		this.mTodayCell = mTodayCell;
	}

	public Day getTomomowCell() {
		return mTomomowCell;
	}

	public void setTomomowCell(Day mTomomowCell) {
		this.mTomomowCell = mTomomowCell;
	}

	public int getYear() {
		return mHelper.getYear();
	}
	    
	public int getMonth() {
		return mHelper.getMonth()+1;
	}
	
	public void refresh() {
		initCells();
		invalidate();
	}
	
	public void nextMonth() {
		mHelper.nextMonth();
		initCells();
		invalidate();
		if(monthChangeListener!=null)
			monthChangeListener.onMonthChanged();
	}
	    
	public void previousMonth() {
		mHelper.previousMonth();
		initCells();
		invalidate();
		if(monthChangeListener!=null)
			monthChangeListener.onMonthChanged();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = -1, height = -1;
        if (widthMode == MeasureSpec.EXACTLY) {
        	width = widthSize;
        } else {
        	width = DEFAULT_BOARD_SIZE;
        	if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
        		width = widthSize;
        	}
        }
        if (heightMode == MeasureSpec.EXACTLY) {
        	height = heightSize;
        } else {
        	height = DEFAULT_BOARD_SIZE;
        	if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
        		height = heightSize;
        	}
        }
        
        if (widthMode != MeasureSpec.EXACTLY) {
        	width = height;
        }
        
        if (heightMode != MeasureSpec.EXACTLY) {
        	height = width;
        }
        
    	if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
    		width = widthSize;
    	}
    	if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
    		height = heightSize;
    	}
    	
    	mCellWidth = (width - getPaddingLeft() - getPaddingRight()) / 7;
        mCellHeight = (height - getPaddingTop() - getPaddingBottom()) / 6;
        setMeasuredDimension(width, height);
        CELL_TEXT_SIZE = mCellHeight * 0.3f;
        initCells();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// draw cells
		for(Day[] week : mCells) {
			for(Day day : week) {
				day.draw(canvas);			
			}
		}
		//draw touched
//		if(mTouchedCell!=null){
//			Rect bound = mTouchedCell.getBound();
//			canvas.drawRect(bound.left, bound.top, bound.right, bound.bottom, mBackgroundColorTouched);
//		}
	}
	
	public Day getCellAtPoint(int x, int y){
		int lx = x - getPaddingLeft();
		int ly = y - getPaddingTop();
		
		int row = (int) (ly / mCellHeight);
		int col = (int) (lx / mCellWidth);
		
		if(col>=0 && col<7 && row>=0 && row<6){
			mTouchedCell = mCells[row][col];
		}else {
			mTouchedCell = null;
		}
		if(mTouchedCell instanceof LastMonthDay || mTouchedCell instanceof NextMonthDay) {
			mTouchedCell = null;
		}
		return mTouchedCell;
	}
	
	private class LastMonthDay extends Day {
		public LastMonthDay(int year, int month, int dayOfMon, Rect rect, float s) {
			super(null, year, month, dayOfMon, rect, s);
			isDraw = false;
		}
	}
	
	
	private class NextMonthDay extends Day {
		public NextMonthDay(int year, int month, int dayOfMon, Rect rect, float s) {
			super(null, year, month, dayOfMon, rect, s);
			isDraw = false;
		}
	}
	
	private class WeekEndDay extends Day {
		public WeekEndDay(Bitmap bitmap, int year, int month, int dayOfMon, Rect rect, float s) {
			super(bitmap ,year, month, dayOfMon, rect, s);
			getTextPaint().setColor(Color.RED);
		}
		
	}
	
	public Day getmTouchedCell() {
		return mTouchedCell;
	}
	
	public void setmTouchedCell(Day mTouchedCell) {
		this.mTouchedCell = mTouchedCell;
	}

	public void setMonthChangeListener(OnMonthChangeListener monthChangeListener) {
		this.monthChangeListener = monthChangeListener;
	}

	public interface OnMonthChangeListener{
		public void onMonthChanged();
	}
}
