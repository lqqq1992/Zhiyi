package com.example.lqqq.imageabout.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by LQQQ1 on 2018/1/29.
 */

public class Compressor {
    private int maxWidth = 480;//压缩宽度
    private int maxHeight = 800;//压缩高度
    private int quality = 100;//压缩质量
    private int maxSize = 500;//最大图片大小
    private int oldSize = 1;
    private ByteArrayOutputStream baos;//存放压缩后的图片数据，用于写入文件

    public Compressor(){}
    public Compressor(int maxWidth,int maxHeight,int quality){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        if(quality<0) this.quality = 0;
        else if(quality>100) this.quality = 100;
        else this.quality = quality;
    }

    public Compressor setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
        return this;
    }
    public Compressor setMaxHeight(int maxHeight){
        this.maxHeight = maxHeight;
        return this;
    }
    public Compressor setQuality(int quality){
        if(quality<0) this.quality = 0;
        else if(quality>100) this.quality = 100;
        else this.quality = quality;
        return this;
    }
    public Compressor setMaxSize(int maxSize){
        this.maxSize = maxSize;
        return this;
    }

    /**
     * 批量压缩
     * @param imageFile
     * @return
     */
    public Compressor compressToTargetSize(File imageFile){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建输出流
        Bitmap bitmap = scale(imageFile);
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        while (baos.toByteArray().length/1024f>=this.maxSize){
            quality = quality - 5;
            if(quality <= 80){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = oldSize*2;
                oldSize *= 2;
                bitmap = BitmapFactory.decodeFile(imageFile.getPath(),options);
                quality = 100;
            }
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        }

        this.baos = baos;
        return this;
    }
    /**
     * 从文件中读取并压缩图片
     * @param imageFile
     * @return
     */
    public Compressor compressImage(File imageFile){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建输出流
        //1.图片旋转问题
        Matrix matrix = null;
        try {
            //矫正角度
            matrix = adjustAngle(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.通过采样率压缩进行像素大小压缩
        Bitmap bitmap = scale(imageFile);
        //3.将旋转和采样压缩后的图片重新绘制
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //4.进行质量压缩
        baos = compressQuality(bitmap);
        //5.将压缩后的数据保存在baos中，用于存储到文件中
        this.baos = baos;
        return this;
    }

    /**
     * 从资源文件中读取并压缩图片
     * @param resources
     * @param id
     * @return
     */
    public Compressor compressImage(Resources resources,int id){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Bitmap bitmap = scale(resources, id);
        baos = compressQuality(bitmap);
        this.baos = baos;
        return this;
    }

    /**
     * 矫正图片角度
     * @param imageFile
     * @return
     * @throws IOException
     */
    public Matrix adjustAngle(File imageFile) throws IOException {
        ExifInterface exif;
        exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        return matrix;
    }

    /**
     * 采样率压缩（尺寸压缩）
     * @param imageFile
     * @return
     */
    public Bitmap scale(File imageFile){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        options.inSampleSize = calculateInSampleSize(options,maxWidth,maxHeight);
        oldSize = options.inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
    }

    /**
     * 采样率压缩（尺寸压缩）
     * @param resources
     * @param id
     * @return
     */
    public Bitmap scale(Resources resources,int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,id,options);
        options.inSampleSize = calculateInSampleSize(options,maxWidth,maxHeight);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources,id,options);
    }

    /**
     * 质量压缩（文件大小压缩）
     * @param bitmap
     * @return
     */
    public ByteArrayOutputStream compressQuality(Bitmap bitmap){
        int quality = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
        while (baos.toByteArray().length/1024f>=this.maxSize){
            quality = quality - 5;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
            if(quality <= 0){
                return baos;
            }
        }
        return baos;
    }

    /**
     * 计算采样率压缩压缩倍率
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
//        if (height > reqHeight || width > reqWidth) {
//            final int heightRatio = Math.round((float) height/ (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
        return inSampleSize;
    }

    /**
     * 保存压缩图片到文件中
     * @param path
     * @param fileName
     * @return
     */
    public File saveImageFile(String path,String fileName){
        if(baos == null || baos.toByteArray().length <= 0){
            return null;
        }
        File file = new File(path,fileName);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(baos.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 添加到UI中
     * @param imageView
     */
    public void addTo(ImageView imageView){
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(baos.toByteArray(),0,baos.toByteArray().length));
    }
}
