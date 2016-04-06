package com.rsandoval.rxjavaexample.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.rsandoval.restexample.R;
import com.rsandoval.rxjavaexample.views.adapters.PostsAdapter;
import com.rsandoval.rxjavaexample.models.Post;
import com.rsandoval.rxjavaexample.presenters.ListPresenter;
import com.rsandoval.rxjavaexample.services.ForumService;
import com.rsandoval.rxjavaexample.views.interfaces.CitiesView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class ListActivity extends ActionBarActivity implements CitiesView {

    @InjectView(R.id.listViewPosts)
    ListView mListViewPosts;

    PostsAdapter mPostsAdapter;

    ListPresenter mListPresenter;
    ForumService mForumService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.inject(this);

        ArrayList<Post> dummyPosts = new ArrayList<Post>();
        mPostsAdapter = new PostsAdapter(this, dummyPosts);
        mListViewPosts.setAdapter(mPostsAdapter);

        mForumService = new ForumService();
        mListPresenter = new ListPresenter(this, mForumService);

    }

    @OnItemClick(R.id.listViewPosts)
    public void onPostSelect(int position) {

        Post p = mPostsAdapter.getItem(position);
        int postId = p.getId();

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("postId", postId);
        startActivity(detailIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListPresenter.getCities();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadCities(List<Post> postList) {
        mPostsAdapter.clear();
        mPostsAdapter.addAll(postList);
        mPostsAdapter.notifyDataSetInvalidated();
    }
}
