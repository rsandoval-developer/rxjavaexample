package com.rsandoval.rxjavaexample.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rsandoval.restexample.R;
import com.rsandoval.rxjavaexample.models.Post;

import java.util.ArrayList;

/**
 * Created by rsandoval on 03/04/16.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    public PostsAdapter(Context ctx, ArrayList<Post> posts) {

        super(ctx, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Post post = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_post_item, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.textViewItemTitle);
        title.setText(post.Nombre);

        return convertView;
    }
}
