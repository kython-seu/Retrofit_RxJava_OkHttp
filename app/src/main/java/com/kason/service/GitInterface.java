package com.kason.service;

import com.kason.bean.UserMessage;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kason_zhang on 9/3/2016.
 */
public interface GitInterface {

    //Get the user's GitHub Message by GET Method
    @GET("/users/{username}")
    public Observable<UserMessage> getUserMessage(@Path("username") String username);
}
