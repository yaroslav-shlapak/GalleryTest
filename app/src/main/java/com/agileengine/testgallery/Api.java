package com.agileengine.testgallery;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Api {

    @GET("/photos?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF&page=2")
    public void getData( Callback<Page> response);//@Path("pageNumber") int pageNumber, @Path("str") String string,

}
