package com.knothing.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by MaZhihua on 2017/9/5.
 */

public class MainActivity extends Activity {

    private Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //方式一：直接传递参数
                Observable<UserInfo> userInfoObservable = BFRequest.getApiService().login("BFTV","1234567")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                // do you logic

                //方式二：参数放到Map中
                HashMap<String,String> params = new HashMap<>();
                params.put("userId","user123");
                params.put("pwd","123456");
                Observable<UserInfo> userDetail = BFRequest.getApiService().userDetail(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                // do you logic

            }
        });
    }
}
