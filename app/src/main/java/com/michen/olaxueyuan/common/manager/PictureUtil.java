package com.michen.olaxueyuan.common.manager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.michen.olaxueyuan.app.SEAPP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by mingge on 16/5/9.
 */
public class PictureUtil {
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static void getSmallBitmap(String filePath, String tmp) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 720, 1280);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            File t = new File(tmp);
            if (!t.getParentFile().exists()) {
                t.getParentFile().mkdirs();
            }
            if (!t.exists()) {
                t.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(t));
            BitmapFactory.decodeFile(filePath, options).compress(Bitmap.CompressFormat.JPEG, 70, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap sharpen(Bitmap src, double weight) {
        double[][] SharpConfig = new double[][]{
                {-1, -1, -1},
                {-1, weight, -1},
                {-1, -1, -1}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(SharpConfig);
        convMatrix.Factor = weight - 8;
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
    }

    public static void sharpeFile(File file) {
        FileOutputStream fos = null;
        Bitmap newBmp = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;//.ARGB_8888;
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bmp.getWidth() < 1000) {
                if (bmp != null) {
                    bmp.recycle();
                }
                return;
            }
            newBmp = sharpen(bmp, 14);
            if (newBmp == null) {
                return;
            }
            fos = new FileOutputStream(file);
            if (fos == null) {
                return;
            }
            newBmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            if (bmp != null) {
                bmp.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (newBmp != null) {
                    newBmp.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isGif(File file) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(file);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }

        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return true;
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                return false;
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void compressImage(String filePath, String tmpPath) {

    }

    /**
     * 转换
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        }
    }

    /**
     * 保存图片到相册
     *
     * @param bitmap
     * @return
     */
    public static boolean saveImageToLocal(Bitmap bitmap) {
        try {
            if (bitmap == null) {
                Logger.e("bitmap is null:" + bitmap);
                return false;
            }
            String name = System.currentTimeMillis() + ".jpg";
            final String urlContent = MediaStore.Images.Media.insertImage(SEAPP.getAppContext().getContentResolver(),
                    bitmap, name, "");
            Uri uria = Uri.parse(urlContent);
            String path = getRealPathFromUri(uria);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(path));
            intent.setData(uri);
            SEAPP.getAppContext().sendBroadcast(intent);
//            if (bitmap != null) {
//                bitmap.recycle();
//            }
            ToastUtil.showToastShort(SEAPP.getAppContext(), "保存到" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取真实路径
     *
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = SEAPP.getAppContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
