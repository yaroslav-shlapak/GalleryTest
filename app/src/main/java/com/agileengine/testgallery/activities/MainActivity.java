package com.agileengine.testgallery.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.agileengine.testgallery.R;
import com.agileengine.testgallery.data.Page;
import com.agileengine.testgallery.data.Photo;
import com.agileengine.testgallery.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {

    private List<Photo> photosList;
    private int pageNumber = 0;
    private String feature = "popular";
    private String consumerKey = "wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF";
    public static final String LOG_TAG = "testgalleryLog";
    private int pagesNumber = 10;
    private boolean loadingFlag = true;
    private Api api;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Callback<Page> pageCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.500px.com").build();

        api = restAdapter.create(Api.class);

        pageCallback = new Callback<Page>() {
            @Override
            public void success(Page page, Response response) {

                List<Photo> tempList = page.getPhotos();
                if(photosList == null) {
                    photosList = tempList;
                    pagesNumber = page.getTotal_pages();
                    gridAdapter = new GridAdapter(getApplicationContext(), photosList);
                    gridView.setAdapter(gridAdapter);
                } else {
                    photosList.addAll(tempList);
                    gridAdapter.notifyDataSetChanged();
                }
                pageNumber = photosList.size() / tempList.size() + 1;
                loadingFlag = false;

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
                fullScreenIntent.putExtra(MainActivity.class.getName() + "imageId", imageId);
                fullScreenIntent.putParcelableArrayListExtra(MainActivity.class.getName() + "photosList", (ArrayList) photosList);
                MainActivity.this.startActivity(fullScreenIntent);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount != 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
                    if(loadingFlag == false) {
                        loadingFlag = true;
                        //Log.d(LOG_TAG, "onScroll");
                        updateUIpage();
                    }

                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });

    }


    private void updateUIpage() {
        if(pageNumber >= pagesNumber) {
            Toast.makeText(getApplicationContext(), "End of feed", Toast.LENGTH_SHORT).show();
            return;
        }
        api.getData(feature, consumerKey, pageNumber, pageCallback);
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
