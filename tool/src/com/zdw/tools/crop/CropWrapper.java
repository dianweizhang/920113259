
package com.zdw.tools.crop;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class CropWrapper {

    public interface HandleCropListener {
        void handleCrop(int resultCode, Intent result);
    }

    private Uri mCaptureUri;
    private Activity mActivity;
    private HandleCropListener mHandleCropListener;

    /**
     * @param context
     * @param listener
     */
    public CropWrapper(Activity context, HandleCropListener listener) {
        mActivity = context;
        mHandleCropListener = listener;
        mCaptureUri = Uri.fromFile(getCaptureTempFile(context));
    }

    public void camera() {
        capturePhotoWithUri(Crop.REQUEST_CAMERA);
    }

    public void pick() {
        Crop.pickImage(mActivity);
    }

    private void capturePhotoWithUri(int requestCode) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureUri);
        }
        mActivity.startActivityForResult(intentFromCapture, requestCode);
    }

    private void beginCrop(Uri source) {
        // Uri outputUri = Uri.fromFile(new File(mActivity.getCacheDir(),
        // "cropped"));
        Uri outputUri = Uri.fromFile(getCroppedFile(mActivity));
        new Crop(source).output(outputUri).asSquare().start(mActivity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == Activity.RESULT_OK) {
            if (result == null) {
                return;
            }
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            beginCrop(mCaptureUri);
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (result == null) {
                return;
            }
            // Uri uri = saveAvatarBitmapByUri(Crop.getOutput(result));
            if (mHandleCropListener != null) {
                mHandleCropListener.handleCrop(resultCode, result);
            }
        } 
    }

    /**
     * @param context
     * @return
     * @comment 获取照相临时图片
     *          External:{externalstorage}/Android/data/{pacakgename}/cache
     */
    private File getCaptureTempFile(Context context) {
        String tempPath = getTempImagePath(context);
        File tempPathFile = new File(tempPath);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
        return new File(tempPath + File.separator + "CaptureTemp.jpg");
    }

    // 获取临时图片保存路径
    private String getTempImagePath(Context context) {
        String state = Environment.getExternalStorageState();
        String savedImgPath;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String externalPicturePath = context.getExternalCacheDir().getAbsolutePath();
            savedImgPath = externalPicturePath;
        } else {
            String internalPicturePath = context.getCacheDir().getAbsolutePath();
            savedImgPath = internalPicturePath;
        }
        return savedImgPath;
    }

    /**
     * @param context
     * @return
     * @comment 获取处理结果图片文件
     *          External:{externalstorage}/Android/data/{pacakgename}/pictures
     */
    private File getCroppedFile(Context context) {
        String croppedPath = getSavedImagePath(context);
        File tempPathFile = new File(croppedPath);
        if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
        return new File(croppedPath + getCharacterAndNumber());
    }

    // 获取处理结果图片保存路径
    private String getSavedImagePath(Context context) {
        String state = Environment.getExternalStorageState();
        String savedImgPath;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String externalPicturePath = context
                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                    + File.separator;
            savedImgPath = externalPicturePath;
        } else {
            String internalPicturePath = context.getFilesDir().getAbsolutePath();
            savedImgPath = internalPicturePath;
        }
        return savedImgPath;
    }

    private String getCharacterAndNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String fileName = formatter.format(curDate);
        return fileName + ".jpg";
    }

}
