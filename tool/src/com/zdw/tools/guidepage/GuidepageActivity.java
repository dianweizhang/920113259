package com.zdw.tools.guidepage;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

import com.zdw.tools.R;

/** What's new �ĵ������� */
public class GuidepageActivity extends Activity {
	/** Viewpager���� */
	private ViewPager viewPager;
	private ImageView imageView;
	/** ����һ�����飬�������ÿ��ҳ��Ҫ��ʾ��View */
	private ArrayList<View> pageViews;
	/** ����һ��imageview���͵����飬������ʾ����СԲ�� */
	private ImageView[] imageViews;
	/** װ��ʾͼƬ��viewgroup */
	private ViewGroup viewPictures;
	/** ����СԲ���viewgroup */
	private ViewGroup viewPoints;
	
	/** ����viewpager���õ��ı���ͼƬ*/
	private int[] bg_drawables = {R.drawable.globalguide_photo1,R.drawable.globalguide_photo2,R.drawable.globalguide_photo3};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();
		pageViews = new ArrayList<View>();
		for(int i=0;i<bg_drawables.length;i++) {
			if(i == bg_drawables.length-1) {
				View view = inflater.inflate(R.layout.viewpager_btn, null);
				view.setBackgroundResource(bg_drawables[i]);
				pageViews.add(view);
			}else{
				View view = inflater.inflate(R.layout.viewpager_text, null);
				view.setBackgroundResource(bg_drawables[i]);
				pageViews.add(view);
			}
		}

		// СԲ�����飬��С��ͼƬ�ĸ���
		imageViews = new ImageView[pageViews.size()];
		// ��ָ����XML�ļ��м�����ͼ
		viewPictures = (ViewGroup) inflater.inflate(R.layout.viewpagers, null);

		viewPager = (ViewPager) viewPictures.findViewById(R.id.guidePagers);
		viewPoints = (ViewGroup) viewPictures.findViewById(R.id.viewPoints);

		// ���СԲ�㵼����ͼƬ
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(GuidepageActivity.this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 0, 5, 0);
			// ��СԲ��Ž�������
			imageViews[i] = imageView;
			// Ĭ��ѡ�е��ǵ�һ��ͼƬ����ʱ��һ��СԲ����ѡ��״̬����������
			if (i == 0)
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
			else
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_unfocused));
			// ��imageviews��ӵ�СԲ����ͼ��
			viewPoints.addView(imageViews[i]);
		}

		setContentView(viewPictures);

		viewPager.setAdapter(new NavigationPageAdapter());
		// Ϊviewpager��Ӽ�������view�����仯ʱ����Ӧ
		viewPager.setOnPageChangeListener(new NavigationPageChangeListener());
	}

	// ����ͼƬview��������������Ҫʵ�ֵ��������ĸ�����
	class NavigationPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// ��ʼ��ÿ��Item
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(pageViews.get(position));
			return pageViews.get(position);
		}

		// ����ÿ��Item
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(pageViews.get(position));
		}

	}

	// viewpager�ļ���������Ҫ��onPageSelectedҪʵ��
	class NavigationPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// ѭ����Ҫ�ǿ��Ƶ�����ÿ��СԲ���״̬
			for (int i = 0; i < imageViews.length; i++) {
				// ��ǰview������СԲ��Ϊѡ��״̬
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_indicator_focused));
				// ��������Ϊ��ѡ��״̬
				if (position != i)
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_indicator_unfocused));
			}
		}

	}
}
