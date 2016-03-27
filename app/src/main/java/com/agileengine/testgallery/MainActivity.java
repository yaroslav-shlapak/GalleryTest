package com.agileengine.testgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {

    private List<Page.Photo> photosList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridview = (GridView) findViewById(R.id.gridView);

        final RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("https://api.500px.com").build();

        Api api = restadapter.create(Api.class);

        api.getData(new Callback<Page>() {
            @Override
            public void success(Page page, Response response) {
                photosList = page.getPhotos();

                gridview.setAdapter(new GridAdapter(getApplicationContext(), photosList));

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                int imageId = (int) parent.getAdapter().getItemId(position);

                Intent fullScreenIntent = new Intent(v.getContext(), FullScreenActivity.class);
                fullScreenIntent.putExtra(MainActivity.class.getName()  + "url", photosList.get(imageId).getImage_url());
                //shows photo name, author name and camera model as an overlay
                fullScreenIntent.putExtra(MainActivity.class.getName()  + "photoName", photosList.get(imageId).getName());
                fullScreenIntent.putExtra(MainActivity.class.getName() + "cameraName", photosList.get(imageId).getCamera());
                fullScreenIntent.putExtra(MainActivity.class.getName() + "authorName", photosList.get(imageId).getUser().getFullname());

                MainActivity.this.startActivity(fullScreenIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


}
