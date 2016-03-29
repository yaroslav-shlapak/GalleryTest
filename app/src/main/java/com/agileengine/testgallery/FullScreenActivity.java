package com.agileengine.testgallery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenActivity extends Activity {
    private String url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        Intent intent = getIntent();



        Bundle extras = intent.getExtras();
        ArrayList<Photo> myList = extras.getParcelableArrayList(MainActivity.class.getName() + "photosList");

        int imageId = extras.getInt(MainActivity.class.getName() + "imageId");
        url = myList.get(imageId).getImage_url();
        String photoName = myList.get(imageId).getName();
        String cameraName = myList.get(imageId).getCamera();
        String authorName = myList.get(imageId).getUser().getFullname();

        ImageView imageView = (ImageView) findViewById(R.id.fullImage);
        TextView photoNameTV = (TextView) findViewById(R.id.photoName);
        TextView cameraNameTV = (TextView) findViewById(R.id.cameraName);
        TextView authorNameTV = (TextView) findViewById(R.id.authorName);
        FloatingActionButton shareButton = (FloatingActionButton) findViewById(R.id.shareButton);
        shareButton.setImageResource(R.drawable.share_android);

        photoNameTV.setText(photoName);
        cameraNameTV.setText(cameraName);
        authorNameTV.setText(authorName);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);

        Picasso.with(getApplicationContext()).load(url).into(imageView);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl(url);
            }
        });

    }

    private void shareTextUrl(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share image URL"));
    }



}