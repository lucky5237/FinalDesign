package zjut.jianlu.breakfast.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import java.io.IOException;

/**
 * 拍照并裁剪功能类
 * 
 * @author Leon Hu create at 2015年6月16日 上午9:41:37
 */
public class CropImageUtil {

    /**
     * 拍照
     * 
     * @param uri
     * @return
     */
    public static Intent capture(Uri uri) {
        // action is capture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }


    public static Intent gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 裁剪
     * 
     * @param uri
     * @param outputX
     * @param outputY
     * @return
     */
    public static Intent crop(Uri uri, int outputX, int outputY) {
        return crop(uri, outputX, outputY, uri);
    }

    /**
     * 裁剪
     * 
     * @param uri
     * @param outputX
     * @param outputY
     * @return
     */
    public static Intent crop(Uri uri, int outputX, int outputY, Uri outUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", outputX);
        intent.putExtra("aspectY", outputY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("output", outUri);
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    /**
     * 读取图片旋转角度
     * 
     * @param path
     * @return
     */
    public static int readPicDegree(String path) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            }
        }

        return degree;
    }

    /**
     * 处理图片，得到旋转后的图片
     */
    public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {
        if (degree == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }
}
