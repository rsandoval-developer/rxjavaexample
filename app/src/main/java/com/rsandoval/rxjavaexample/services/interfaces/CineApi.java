package com.rsandoval.rxjavaexample.services.interfaces;

import com.rsandoval.rxjavaexample.models.Post;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rsandoval on 04/04/2016.
 */
public interface CineApi {

    @GET("/ObtenerCiudadesPaises")
    public Observable<List<Post>>
    getCities(@Query("idsPaises") String idsPaises);
}
