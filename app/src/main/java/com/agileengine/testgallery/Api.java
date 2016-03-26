package com.agileengine.testgallery;

import retrofit.Callback;
import retrofit.http.GET;

public interface Api {

    @GET("/v1/photos?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF&page=3")
    public void getData(Callback<Page> response);


}
