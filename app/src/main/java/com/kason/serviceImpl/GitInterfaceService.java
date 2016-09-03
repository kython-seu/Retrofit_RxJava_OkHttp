package com.kason.serviceImpl;

import android.util.Log;

import com.kason.service.GitInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kason_zhang on 9/3/2016.
 */
public class GitInterfaceService {

    private static final String TAG = "GitInterfaceService";
    //the base url
    public static final String BASE_URL = "https://api.github.com";

    private GitInterface gitInterface;

    private GitInterfaceService(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()).build();
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        gitInterface = client.create(GitInterface.class);
    }

    public GitInterface getGitInterface() {
        return gitInterface;
    }

    //design  a single insatance;
    private static class SingleInstance{
        private static final GitInterfaceService instance = new GitInterfaceService();
    }
    public static GitInterfaceService getInstance(){
        return SingleInstance.instance;
    }

    class LoggingInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
