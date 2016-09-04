package com.kason.serviceImpl;

import android.util.Log;

import com.kason.service.DouBanApiInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by IBM on 2016/9/4.
 */
public class DouBanService {
    private static final String TAG = "DouBanService";
    //private DouBanService douBanService;
    private static final  String BASE_URL = "https://api.douban.com";
    public static DouBanApiInterface douBanApiInterface;
    private DouBanService(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()).build();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        douBanApiInterface = client.create(DouBanApiInterface.class);
    }
    private static class Signal{
        private static  DouBanService douBanService = new DouBanService();
    }
    public static DouBanService getDouBanService(){
        return Signal.douBanService;
    }
    /*public DouBanApiInterface getDouBanInstace(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()).build();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        DouBanApiInterface douBanApiInterface = client.create(DouBanApiInterface.class);
        return douBanApiInterface;
    }*/
    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat
                    ("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String time = simpleDateFormat.format(calendar.getTime());
            Log.i(TAG, time+" Sending request "+request.url()
                    +" on "+chain.connection()+"\n"+request.headers());

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            time = simpleDateFormat.format(calendar.getTime());

            Log.i(TAG, time+" Received response for "+response.request().url()
                    +" in "+(t2 - t1) / 1e6d+"\n"+response.headers());
            return response;
        }
    }
}
