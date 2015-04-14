package com.zdw.tools.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zdw.tools.R;
import com.zdw.tools.widget.satellitemenu.SatelliteMenu;
import com.zdw.tools.widget.satellitemenu.SatelliteMenu.SateliteClickedListener;
import com.zdw.tools.widget.satellitemenu.SatelliteMenuItem;

public class SatelliteMenuActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sat_main1);
        
        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);
        
//		  Set from XML, possible to programmatically set        
//        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170, getResources().getDisplayMetrics());
//        menu.setSatelliteDistance((int) distance);
//        menu.setExpandDuration(500);
//        menu.setCloseItemsOnClick(false);
//        menu.setTotalSpacingDegree(60);
        
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.drawable.sat_ic_1));
        items.add(new SatelliteMenuItem(4, R.drawable.sat_ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.sat_ic_4));
        items.add(new SatelliteMenuItem(3, R.drawable.sat_ic_5));
        items.add(new SatelliteMenuItem(2, R.drawable.sat_ic_6));
        items.add(new SatelliteMenuItem(1, R.drawable.sat_ic_2));
//        items.add(new SatelliteMenuItem(5, R.drawable.sat_item));
        menu.addItems(items);        
        
        menu.setOnItemClickedListener(new SateliteClickedListener() {
			
			public void eventOccured(int id) {
				Log.i("sat", "Clicked on " + id);
			}
		});
    }
}