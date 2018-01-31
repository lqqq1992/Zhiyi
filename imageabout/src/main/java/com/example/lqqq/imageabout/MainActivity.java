package com.example.lqqq.imageabout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lqqq.imageabout.utils.Compressor;

import java.io.File;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView originalImage,newImage;
    private TextView size,newSize;
    private Button compress,save;
    private EditText newWidth,newHeight,quality;
    private Compressor compressor;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File(Environment.getExternalStorageDirectory(),"original.jpg");
        compressor = new Compressor();
//        newImage.setImageBitmap(compressor.compressImage(getResources(),R.mipmap.original));
        init();
    }
    private void init(){
        originalImage = findViewById(R.id.original_image);
        newImage = findViewById(R.id.new_image);
        size = findViewById(R.id.size);
        newSize = findViewById(R.id.new_size);
        compress = findViewById(R.id.compress);
        save = findViewById(R.id.save);
        newWidth = findViewById(R.id.new_width);
        newHeight = findViewById(R.id.new_height);
        quality = findViewById(R.id.quality);

        compress.setOnClickListener(this);
        save.setOnClickListener(this);
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            originalImage.setImageBitmap(bitmap);
            size.setText(getReadableFileSize(file.length()));
        }else{
            Toast.makeText(this,"图片不存在！",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            newImage.setImageBitmap(compressor
                    .setMaxWidth(getContent(newWidth,1000))
                    .setMaxHeight(getContent(newHeight,1000))
                    .setMaxSize(getContent(quality,500))
//                    .setQuality(getContent(quality,500))
//                    .setQuality(getQuality(file.length(),Integer.parseInt(quality.getText().toString())))
                    .compressImage(file));
        }
    }

    @Override
    public void onClick(View v) {
        if (!file.exists()){
            Toast.makeText(this,"图片不存在！",Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()){
            case R.id.compress:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    newImage.setImageBitmap(compressor
                            .setMaxWidth(getContent(newWidth,1000))
                            .setMaxHeight(getContent(newHeight,1000))
                            .setMaxSize(getContent(quality,500))
//                            .setQuality(getContent(quality,500))
//                            .setQuality(getQuality(file.length(),Integer.parseInt(quality.getText().toString())))
                            .compressImage(file));
                }
                break;
            case R.id.save:
                String path = Environment.getExternalStorageDirectory().getPath()+"/ImageCache";
                File newFile = compressor.saveImageFile(path,"cache.jpg");
                if (newFile.exists()) newSize.setText(getReadableFileSize(newFile.length()));
                break;
                default:
        }
    }
    public int getContent(EditText editText,int defaultValue){
        if(editText.getText() == null || editText.getText().equals("")) return defaultValue;
        return Integer.parseInt(editText.getText().toString());
    }
    public String getReadableFileSize(long size) {
        Log.e("====------size",size+"");
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
