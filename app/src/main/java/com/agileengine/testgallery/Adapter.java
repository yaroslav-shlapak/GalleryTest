package com.agileengine.testgallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends ArrayAdapter<Page.Photo> {

    private Context context;
    private List<Page.Photo> photos;
    public Adapter(Context context, int resource, List<Page.Photo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.photos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_file, parent, false);

        Page.Photo photo = photos.get(position);

        TextView photoName = (TextView) view.findViewById(R.id.photoName);
        TextView authorName = (TextView) view.findViewById(R.id.authorName);
        TextView cameraName = (TextView) view.findViewById(R.id.cameraName);
        TextView lensName = (TextView) view.findViewById(R.id.lensName);

        photoName.setText(photo.getName());
        authorName.setText(photo.getUser().getFullname());
        cameraName.setText(photo.getCamera());
        lensName.setText(photo.getLens());

        ImageView img = (ImageView) view.findViewById(R.id.img);
        Picasso.with(getContext()).load(photo.getImage_url()).resize(500,500).into(img);
        return view;
    }
}
