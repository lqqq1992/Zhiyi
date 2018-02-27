package com.example.lqqq.careui.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.lqqq.careui.R;
import com.example.lqqq.careui.contract.MainInterface;
import com.example.lqqq.careui.view.fragment.DoctorsFragment;
import com.example.lqqq.careui.view.fragment.MineFragment;
import com.example.lqqq.careui.view.fragment.RemindPagerFragment;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements MainInterface,View.OnClickListener{

    private TextView doctors,remind,mine;//医生，提醒，我的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void initView() {
        doctors = findViewById(R.id.doctors);
        doctors.setOnClickListener(this);
        remind = findViewById(R.id.remind);
        remind.setOnClickListener(this);
        mine = findViewById(R.id.mine);
        mine.setOnClickListener(this);

        setUI(doctors);
        setFragment(DoctorsFragment.newInstance());
    }

    /**
     * 初始化fragment
     * @param fragment
     */
    @Override
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    /**
     * 切换fragment
     * @param fragment
     */
    @Override
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * 切换fragment同时，修改UI
     * @param textView
     */
    @Override
    public void setUI(TextView textView) {
        doctors.setTextColor(Color.parseColor("#888888"));
        remind.setTextColor(Color.parseColor("#888888"));
        mine.setTextColor(Color.parseColor("#888888"));
        textView.setTextColor(Color.parseColor("#22C3DC"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.doctors:
                setUI(doctors);
                changeFragment(DoctorsFragment.newInstance());
                break;
            case R.id.remind:
                setUI(remind);
                changeFragment(RemindPagerFragment.newInstance());
                break;
            case R.id.mine:
                setUI(mine);
                changeFragment(MineFragment.newInstance());
                break;
        }
    }
}
