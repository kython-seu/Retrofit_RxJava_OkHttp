package com.ui.kason_zhang.retrofit_rxjava_okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kason.service.GitInterface;
import com.kason.bean.UserMessage;
import com.kason.serviceImpl.GitInterfaceService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String USERNAME = "tom";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GitInterface gitInterface = GitInterfaceService.getInstance().getGitInterface();
        gitInterface.getUserMessage(USERNAME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "ERROR : "+e.getMessage() );
                    }

                    @Override
                    public void onNext(UserMessage userMessage) {
                        Log.i(TAG, "result: "+userMessage.toString());
                    }
                });

    }
}
