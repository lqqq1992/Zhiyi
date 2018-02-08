package com.example.lqqq.push;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送设置界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button stop,resume,setAlias,addTag,getRegisterID;//停止接收，恢复接收,设置别名，添加标签
    private EditText alias,tag;//别名，标签
    private Set<String> tags;//标签集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tags = new HashSet<>();
        init();
    }
    private void init(){
        stop = findViewById(R.id.stop);
        resume = findViewById(R.id.resume);
        setAlias = findViewById(R.id.set_alias);
        addTag = findViewById(R.id.add_tag);
        getRegisterID = findViewById(R.id.get_register_id);

        alias = findViewById(R.id.alias);
        tag = findViewById(R.id.tag);

        stop.setOnClickListener(this);
        resume.setOnClickListener(this);
        setAlias.setOnClickListener(this);
        addTag.setOnClickListener(this);
        getRegisterID.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean isStop = JPushInterface.isPushStopped(getApplicationContext());//推送服务是否活动
        Log.e("====>>>isStop",isStop+"");
        switch (v.getId()){
            case R.id.stop:
                //停止接收推送
                JPushInterface.stopPush(getApplicationContext());
                break;
            case R.id.resume:
                //继续接收推送
                JPushInterface.resumePush(getApplicationContext());
                break;
            case R.id.set_alias:
                //设置别名
                JPushInterface.setAlias(this,1,getContent(alias,"alias"));
                break;
            case R.id.add_tag:
                //添加标签
                tags.add(getContent(tag,"tag"));
                JPushInterface.addTags(this,1,tags);
                break;
            case R.id.get_register_id:
                //获取注册ID
                String id = JPushInterface.getRegistrationID(this);
                Toast.makeText(this,"注册ID："+id,Toast.LENGTH_LONG).show();
                default:
        }
    }
    //获取文本内容
    public String getContent(EditText editText, String defaultValue) {
        if (editText.getText() == null || editText.getText().equals("")) return defaultValue;
        return editText.getText().toString().trim();
    }
}
