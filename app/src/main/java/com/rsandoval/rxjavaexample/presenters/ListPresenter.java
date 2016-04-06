package com.rsandoval.rxjavaexample.presenters;

import com.rsandoval.rxjavaexample.models.Post;
import com.rsandoval.rxjavaexample.presenters.inerfaces.getCitiesListener;
import com.rsandoval.rxjavaexample.services.ForumService;
import com.rsandoval.rxjavaexample.views.interfaces.CitiesView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rsandoval on 03/04/16.
 */
public class ListPresenter implements getCitiesListener {

    ForumService mForum;
    private CitiesView mListener;
    private ArrayList<Post> mAddedApps = new ArrayList<>();
    private String idPaises = "1,2,3,4,5,6,9,15";


    public ListPresenter(CitiesView listener, ForumService forum) {
        mListener = listener;
        mForum = forum;
    }

    @Override
    public void getCities() {

        Observable<List<Post>> observable = mForum.getApi().getCities(idPaises);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Post> postList) {
                        mListener.loadCities(postList);
                    }
                });

    }
}
