package com.rsandoval.rxjavaexample.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.rsandoval.restexample.R;
import com.rsandoval.rxjavaexample.presenters.DetailPresenter;
import com.rsandoval.rxjavaexample.services.Download;
import com.rsandoval.rxjavaexample.views.interfaces.DownloadView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.subjects.PublishSubject;


public class DetailActivity extends ActionBarActivity implements DownloadView {

    @InjectView(R.id.arc_progress)
    ArcProgress mArcProgress;

    @InjectView(R.id.button_download)
    Button mButton;

    DetailPresenter mDetailPresenter;
    Download mDownload;

    protected int mPostId;

    private PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        mPostId = getIntent().getIntExtra("postId", 0);
        mDownload = new Download();
        mDetailPresenter = new DetailPresenter(this, this, mDownload, mDownloadProgress, mPostId);
    }

    @OnClick(R.id.button_download)
    void download() {
        mDetailPresenter.download();
        mButton.setText(getString(R.string.descargando));
        mButton.setClickable(false);
    }

    @Override
    public void download(Integer integer) {
        mArcProgress.setProgress(integer);
    }

    @Override
    public void resetDownload() {
        mButton.setText(getString(R.string.descargar_cartelera));
        mButton.setClickable(true);
        mArcProgress.setProgress(0);
    }
}
