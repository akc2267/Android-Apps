package com.example.alexandercheng.hw5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

/**
 * Created by alexandercheng on 10/8/15.
 */
public class DynamicAdapter extends ArrayAdapter<RedditRecord> implements URLFetch.Callback {
    private int layoutResourceId;
    private List<RedditRecord> data;

    public DynamicAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DynamicAdapter(Context context, int layoutId, List<RedditRecord> list) {
        super(context, layoutId, list);
        layoutResourceId = layoutId;
        data = list;


    }
    @Override
    public View getView(int index, View row, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        row = inflater.inflate(layoutResourceId, parent, false);
        TextView text = (TextView) row.findViewById(R.id.list_row_text);
        text.setText(data.get(index).title);

        ImageView thumbnail= (ImageView) row.findViewById(R.id.list_row_image);

        if(BitmapCache.getInstance().getBitmap(data.get(index).thumbnailURL.toString())!=null){
            thumbnail.setImageBitmap(BitmapCache.getInstance().getBitmap(data.get(index).thumbnailURL.toString()));
        }
        else {
            thumbnail.setImageBitmap(BitmapCache.defaultThumbnailBitmap);
                    fetchStart(data.get(index).thumbnailURL);
        }

        if(BitmapCache.getInstance().getBitmap(data.get(index).imageURL.toString())==null){
            fetchStart(data.get(index).imageURL);
        }

        return row;
    }

    @Override
    public void fetchStart(URL url) {
        new URLFetch(this, url, false);

    }

    @Override
    public void fetchComplete(String result) {
        notifyDataSetChanged();
    }

    @Override
    public void fetchCancel(String url) {

    }
}

