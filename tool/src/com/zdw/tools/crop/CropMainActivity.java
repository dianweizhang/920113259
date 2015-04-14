package com.zdw.tools.crop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.zdw.tools.R;
import com.zdw.tools.crop.CropWrapper.HandleCropListener;

public class CropMainActivity  extends Activity {

    private ImageView resultView;
    private CropWrapper mCropWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_main);
        resultView = (ImageView) findViewById(R.id.result_image);
        mCropWrapper = new CropWrapper(this, new HandleCropListener() {
			@Override
			public void handleCrop(int resultCode, Intent result) {
				 if (resultCode == RESULT_OK) {
			            resultView.setImageURI(Crop.getOutput(result));
			        } else if (resultCode == Crop.RESULT_ERROR) {
			            Toast.makeText(CropMainActivity.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
			        }
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select) {
            resultView.setImageDrawable(null);
            mCropWrapper.pick();
            return true;
        }else if(item.getItemId() == R.id.action_camera) {
        	resultView.setImageDrawable(null);
        	mCropWrapper.camera();
        	 return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
		if(mCropWrapper != null){
			mCropWrapper.onActivityResult(requestCode, resultCode, result);
		}
    }
}

