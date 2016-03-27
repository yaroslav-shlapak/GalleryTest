package com.agileengine.testgallery;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullScreenActivity extends Activity {
    private String url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        Intent intent = getIntent();

        long imageId = intent.getExtras().getLong(MainActivity.class.getName());


        url = intent.getExtras().getString(MainActivity.class.getName() + "url");
        String photoName = intent.getExtras().getString(MainActivity.class.getName() + "photoName");
        String cameraName = intent.getExtras().getString(MainActivity.class.getName() + "cameraName");
        String authorName = intent.getExtras().getString(MainActivity.class.getName() + "authorName");

        ImageView imageView = (ImageView) findViewById(R.id.fullImage);
        TextView photoNameTV = (TextView) findViewById(R.id.photoName);
        TextView cameraNameTV = (TextView) findViewById(R.id.cameraName);
        TextView authorNameTV = (TextView) findViewById(R.id.authorName);
        Button shareButton = (Button) findViewById(R.id.shareButton);

        photoNameTV.setText(photoName);
        cameraNameTV.setText(cameraName);
        authorNameTV.setText(authorName);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);

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
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title");
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share image URL"));
    }



}