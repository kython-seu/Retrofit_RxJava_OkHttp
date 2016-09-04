package com.kason.service;

import com.kason.bean.DouBan;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by IBM on 2016/9/4.
 */
public interface DouBanApiInterface  {

    @GET("/v2/book/search")
    Observable<DouBan> getBookMessage(@Query("q") String name,@Query("tag") String tag
            ,@Query("start") int start,@Query("count") int count);
}
