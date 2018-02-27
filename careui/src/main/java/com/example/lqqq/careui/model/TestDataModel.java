package com.example.lqqq.careui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LQQQ1 on 2018/2/27.
 */

public class TestDataModel {
    public TestDataModel(){}
    public List<String> getDoctors(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            names.add("名字" + i);
        }
        return names;
    }
}
