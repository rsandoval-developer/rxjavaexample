package com.rsandoval.rxjavaexample.presenters;

import android.content.Context;
import android.util.Log;

import com.rsandoval.restexample.R;
import com.rsandoval.rxjavaexample.presenters.inerfaces.DownloadListener;
import com.rsandoval.rxjavaexample.services.Download;
import com.rsandoval.rxjavaexample.views.interfaces.DownloadView;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by rsandoval on 03/04/16.
 */
public class DetailPresenter implements DownloadListener {

    Download mDownload;
    DownloadView mDownloadViewLisner;
    private PublishSubject<Integer> mDownloadProgress;
    private Context mContext;
    private int mId;
    String destination;
    String nombreFile = "cine.sqlite";

    public DetailPresenter(Context context, DownloadView listener, Download download, PublishSubject<Integer> downloadProgress, int id) {
        mContext = context;
        mDownloadViewLisner = listener;
        mDownloadProgress = downloadProgress;
        mDownload = download;
        mId = id;
    }

    @Override
    public void download() {
        mDownloadProgress
                .distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mDownloadViewLisner.download(integer);
                    }
                });
        destination = "/data/data/" + mContext.getPackageName() + "/databases/";
        if (!ExisteDirectorioBD()) {
            new File(destination).mkdir();
        }
        mDownload.obserbableDownload(mContext.getString(R.string.url_obtener_db, mId), destination, nombreFile, mDownloadProgress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    mDownloadViewLisner.resetDownload();
                }, error -> {
                    mDownloadViewLisner.resetDownload();
                });
    }

    private boolean ExisteDirectorioBD() {
        File f = new File(destination);
        if (f.exists() && f.isDirectory()) {
            return true;
        }
        return false;
    }
}
