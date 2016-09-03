package com.kason.serviceImpl;

import android.test.AndroidTestCase;
import android.util.Log;

import com.kason.bean.UserMessage;
import com.kason.service.GitInterface;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kason_zhang on 9/3/2016.
 */
public class GitInterfaceServiceTest extends AndroidTestCase{

    public void testDemo(){
        GitInterface gitInterface = GitInterfaceService.getInstance().getGitInterface();
        gitInterface.getUserMessage("tom")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", "ERROR : "+e.getMessage() );
                    }

                    @Override
                    public void onNext(UserMessage userMessage) {
                        Log.i("test", "result: "+userMessage.toString());
                    }
                });

    }

}