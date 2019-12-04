package com.jaiveer.jaiblog;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public class BloogerApi {

    public static final String BASE_URL = "your blogger url goes here";
    public static final String API_KEY = "bloger Api key goes here";

    public static PostService postService = null;

    public static PostService getService(){

        if(postService == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService =retrofit.create(PostService.class);

        }
        return postService;

    }

    /*public interface PostService {
        @GET
        Call<PostList> getPostList(@Url String url);
    }*/
    public interface PostService {
        @GET("?key="+API_KEY)
        Call<PostList> getPostList();

        @GET("{id}")
        Call<PostList> getPostUsingId(@Path("id") String id);
    }




}
