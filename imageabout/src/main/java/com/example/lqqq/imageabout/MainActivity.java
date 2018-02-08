package com.example.lqqq.imageabout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lqqq.imageabout.utils.Compressor;

import java.io.File;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView originalImage, newImage;//原始图片，压缩后图片
    private TextView size, newSize;//原始图片大小，新图片大小
    private Button compress, save, compressMore;//压缩，保存，批量压缩
    private EditText newWidth, newHeight, quality;//新宽度，新高度，最大大小
    private Compressor compressor;//压缩工具类
    private File file;//原始图片文件
    private Handler handler;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(Environment.getExternalStorageDirectory(), "original.jpg");
        handler = new Handler();
        compressor = new Compressor();
//        newImage.setImageBitmap(compressor.compressImage(getResources(),R.mipmap.original));
        init();
    }

    private void init() {
        originalImage = findViewById(R.id.original_image);
        newImage = findViewById(R.id.new_image);
        size = findViewById(R.id.size);
        newSize = findViewById(R.id.new_size);
        compress = findViewById(R.id.compress);
        save = findViewById(R.id.save);
        compressMore = findViewById(R.id.compress_more);
        newWidth = findViewById(R.id.new_width);
        newHeight = findViewById(R.id.new_height);
        quality = findViewById(R.id.quality);

        compress.setOnClickListener(this);
        save.setOnClickListener(this);
        compressMore.setOnClickListener(this);
        getPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                originalImage.setImageBitmap(bitmap);
                size.setText(getReadableFileSize(file.length()));
            } else {
                Toast.makeText(this, "图片不存在！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!file.exists()) {
            Toast.makeText(this, "图片不存在！", Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            //压缩单张图片并显示
            case R.id.compress:
                compressor.setMaxWidth(getContent(newWidth, 1000))//宽度设置
                          .setMaxHeight(getContent(newHeight, 1000))//高度设置
                          .setMaxSize(getContent(quality, 500))//最大图片大小
                          .compressImage(file)//压缩
                          .addTo(newImage);//添加到UI
                break;
            //保存图片到SD卡中
            case R.id.save:
                String path = Environment.getExternalStorageDirectory().getPath() + "/ImageCache";
                File newFile = compressor.saveImageFile(path, "cache.jpg");
                if (newFile.exists()) newSize.setText(getReadableFileSize(newFile.length()));
                break;
            //批量压缩并保存
            case R.id.compress_more:
                compressMore();
            default:
        }
    }

    /**
     * 获取文本内容
     * @param editText
     * @param defaultValue
     * @return
     */
    public int getContent(EditText editText, int defaultValue) {
        if (editText.getText() == null || editText.getText().equals("")) return defaultValue;
        return Integer.parseInt(editText.getText().toString());
    }

    /**
     * 单位换算
     * @param size
     * @return
     */
    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 判断是否有读取文件权限，没有就申请获取，有就加载图片
     */
    public void getPermission(){
        if (ContextCompat.checkSelfPermission(this,//查看是否有权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,//申请权限
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            if (file.exists()) {//文件是否存在
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                originalImage.setImageBitmap(bitmap);
                size.setText(getReadableFileSize(file.length()));
            } else {
                Toast.makeText(this, "图片不存在！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 批量压缩图片并保存
     */
    public void compressMore(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.setTitle("压缩中...").create();
        dialog.show();
        for (int j = 0; j < 5; j++) {//循环开启5个线程
            final Compressor compressor = new Compressor();
            final int k = j;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Environment.getExternalStorageDirectory().getPath() + "/ImageCache";
                    for (int i = 0; i < 20; i++) {//动态设置图片像素
                        compressor.setMaxWidth(6000 / (k + 2))
                                .setMaxHeight(4000 / (k + 2))
                                .setMaxSize(2000 - i * 100)
                                .compressToTargetSize(file);//压缩到指定大小
                        compressor.saveImageFile(path, "cache" + k + "_" + i + ".jpg");//保存图片
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (++count == 5) {
                                count = 0;
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }).start();
        }
    }
}
