package com.rsandoval.rxjavaexample.views.interfaces;

import com.rsandoval.rxjavaexample.models.Post;

import java.util.List;

/**
 * Created by rsandoval on 01/04/2016.
 */
public interface CitiesView {
    void loadCities(List<Post> postList);
}
