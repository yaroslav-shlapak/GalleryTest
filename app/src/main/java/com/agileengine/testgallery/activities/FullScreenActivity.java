package com.agileengine.testgallery.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agileengine.testgallery.R;
import com.agileengine.testgallery.data.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenActivity extends Activity {
    private String url;
    private ImageView imageView;
    private TextView photoNameTV;
    private TextView cameraNameTV;
    private TextView authorNameTV;
    private int imageId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        Intent intent = getIntent();



        Bundle extras = intent.getExtras();
        final ArrayList<Photo> photosList = extras.getParcelableArrayList(MainActivity.class.getName() + "photosList");
        imageId = extras.getInt(MainActivity.class.getName() + "imageId");


        imageView = (ImageView) findViewById(R.id.fullImage);
        photoNameTV = (TextView) findViewById(R.id.photoName);
        cameraNameTV = (TextView) findViewById(R.id.cameraName);
        authorNameTV = (TextView) findViewById(R.id.authorName);

        FloatingActionButton shareButton = (FloatingActionButton) findViewById(R.id.shareButton);
        shareButton.setImageResource(R.drawable.share_android);

        setUI(photosList, imageId);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextUrl(url);
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                if (imageId < photosList.size() - 1) {
                    imageId++;
                } else {
                    Toast.makeText(FullScreenActivity.this, "End of the photos list", Toast.LENGTH_SHORT).show();
                }
                setUI(photosList, imageId);
            }

            @Override
            public void onSwipeRight() {
                if (imageId > 0) {
                    imageId--;
                } else {
                    Toast.makeText(FullScreenActivity.this, "Swipe right, this is the first image" , Toast.LENGTH_SHORT).show();
                }
                setUI(photosList, imageId);
            }
        });

    }

    private void setUI(ArrayList<Photo> photosList, int imageId) {
        url = photosList.get(imageId).getImage_url();
        String photoName = photosList.get(imageId).getName();
        String cameraName = photosList.get(imageId).getCamera();
        String authorName = photosList.get(imageId).getUser().getFullname();

        photoNameTV.setText(photoName);
        cameraNameTV.setText(cameraName);
        authorNameTV.setText(authorName);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);

        Picasso.with(getApplicationContext()).load(url).into(imageView);
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