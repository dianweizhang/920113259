package com.zdw.tools.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zdw.tools.R;
import com.zdw.tools.widget.leftslidemenu.FlipperLayout;

public class LeftSlideMenuActivity extends Activity{

	private Handler rootHandler;
	protected FlipperLayout mRoot;
	private LayoutInflater layoutInflater;
	private View menu_view;
	private View content_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootHandler = new Handler();
		initViewNavMenu(R.layout.cal_calendar_main);
	}
	
	protected void initViewNavMenu(int resId){
		mRoot = new FlipperLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRoot.setLayoutParams(params);
		layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menu_view = layoutInflater.inflate(R.layout.leftslidemenu_main_menu, null);
		content_view = layoutInflater.inflate(resId, null);
		
		//左滑时  菜单与View之间的垂直阴影                                                                                                                                                                                                                                                                                   
		View main_layout = layoutInflater.inflate(R.layout.leftslidemenu_yinying, null);
		LinearLayout l = (LinearLayout) main_layout.findViewById(R.id.main_layout);
		l.addView(content_view,LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		/**
		 * 创建菜单界面和内容首页界面,并添加到容器中,用于初始显示
		 */
		mRoot.addView(menu_view, params);
		mRoot.addView(main_layout, params);
		
		setContentView(mRoot);
		ListView lv_menu = (ListView) menu_view.findViewById(R.id.lv_menu);
		lv_menu.setAdapter(new MenuAdapter(this));
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				closeMenu();
			}
		});
	}
	
	private void closeMenu() {
		if(rootHandler == null) {
			rootHandler = new Handler();
		}
		rootHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mRoot.close();
			}
		}, 500);
	}
	
	protected void showMenu() {
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}	
	}
	
	
	class MenuAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		private String[] title = new String[]{"首页","设置","帮助","反馈","退出"};
		public MenuAdapter(Context context) {  
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return title.length;
		}

		@Override
		public Object getItem(int arg0) {
			return title[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			ViewHold viewHold;
			if (convertView == null) {
				viewHold = new ViewHold();
				convertView = mInflater.inflate(R.layout.leftslidemenu_menu_item, null);
				viewHold.content_TV = (TextView) convertView.findViewById(R.id.main_menu_item_tv);
//				viewHold.icon_IV = (ImageView) convertView.findViewById(R.id.main_menu_item_icon);
				convertView.setTag(viewHold);
			}else {
				viewHold = (ViewHold) convertView.getTag();
			}
			viewHold.content_TV.setText(title[arg0]);
//			viewHold.icon_IV.setBackgroundResource(menu_icon[arg0]);
			return convertView;
		}

		class ViewHold {
			TextView content_TV;
			ImageView icon_IV;
		}
	}
}
