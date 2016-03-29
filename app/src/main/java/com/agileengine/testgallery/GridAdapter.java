package com.agileengine.testgallery;


import android.app.Activity;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<Page.Photo> photos;
    private ImageView imageView;

    public GridAdapter(Context context, List<Page.Photo> objects) {

        this.context = context;
        this.photos = objects;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_file, parent, false);
        } else {
            view = convertView;
        }

        Page.Photo photo = photos.get(position);
        imageView = (ImageView) view.findViewById(R.id.img);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);

        Picasso.with(context).load(photo.getImage_url()).into(imageView);

        return view;
    }

}
