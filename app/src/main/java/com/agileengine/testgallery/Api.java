package com.agileengine.testgallery;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Api {

    @GET("/v1/photos")//?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF&page=2
    public void getData(@Query("feature") String feature, @Query("consumer_key") String consumerKey, @Query("page") int pageNumber, Callback<Page> response);

}
