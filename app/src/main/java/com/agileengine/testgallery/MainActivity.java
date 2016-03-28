package com.agileengine.testgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
    public static int pageNumber = 2;
    private int totalItemsAlreadyDownloaded = 0;
    public static final int ITEMS_PER_PAGE = 20;
    private Api api;
    private GridView gridView;
    private GridAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.500px.com/v1").build();

        api = restAdapter.create(Api.class);

        pageCallback = new Callback<Page>() {
            @Override
            public void success(Page page, Response response) {

                if(photosList == null) {
                    photosList = page.getPhotos();
                    gridAdapter = new GridAdapter(getApplicationContext(), photosList);
                    gridView.setAdapter(gridAdapter);
                } else {
                    photosList.addAll(page.getPhotos());
                    gridAdapter.notifyDataSetChanged();
                    //pageNumber++;
                }

                //gridAdapter.
                //gridAdapter = new GridAdapter(getApplicationContext(), photosList);
                //gridView.setAdapter(gridAdapter);
                //pageNumber++;

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        };
        updateUIpage();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                int imageId = (int) parent.getAdapter().getItemId(position);

                Intent fullScreenIntent = new Intent(v.getContext(), FullScreenActivity.class);
                fullScreenIntent.putExtra(MainActivity.class.getName() + "url", photosList.get(imageId).getImage_url());
                //shows photo name, author name and camera model as an overlay
                fullScreenIntent.putExtra(MainActivity.class.getName() + "photoName", photosList.get(imageId).getName());
                fullScreenIntent.putExtra(MainActivity.class.getName() + "cameraName", photosList.get(imageId).getCamera());
                fullScreenIntent.putExtra(MainActivity.class.getName() + "authorName", photosList.get(imageId).getUser().getFullname());

                MainActivity.this.startActivity(fullScreenIntent);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    updateUIpage();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });

    }


    private void updateUIpage() {
        api.getData( pageCallback);
    }

    private Callback<Page> pageCallback;

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
