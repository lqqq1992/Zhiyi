package com.example.lqqq.careui.presenter;

import com.example.lqqq.careui.model.TestDataModel;
import com.example.lqqq.careui.view.fragment.DoctorsFragment;

/**
 * Created by LQQQ1 on 2018/2/27.
 */

public class DoctorsPresenter {
    private  DoctorsFragment view;
    private TestDataModel model;
    public DoctorsPresenter(DoctorsFragment view){
        this.view = view;
        model = new TestDataModel();
    }
    public void setData(){
        view.setNames(model.getDoctors());
    }
}
