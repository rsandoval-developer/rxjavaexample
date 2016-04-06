package com.rsandoval.rxjavaexample.services;

import com.rsandoval.rxjavaexample.services.interfaces.CineApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by rsandoval on 03/04/16.
 */
public class ForumService {

    private static final String FORUM_SERVER_URL = "http://api.cinepolis.com.mx/Consumo.svc/json";
    private CineApi mCineApi;

    public ForumService() {


        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(FORUM_SERVER_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mCineApi = restAdapter.create(CineApi.class);
    }

    public CineApi getApi() {

        return mCineApi;
    }


}
