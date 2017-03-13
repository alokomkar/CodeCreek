package com.sortedqueue.programmercreek.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alok on 13/03/17.
 */

public class RetrofitCreator {

    //http://square.github.io/retrofit/

    private static final String BASE_URL = "http://fa839326.compilers.sphere-engine.com/api/v3/";
    private static final String TOKEN_COMPILER_API = "faed39ebdab374918efffba2d99bfd86";
    private static final String TOKEN_PROBLEM_API = "e561399f6fc1fd3d18525d8056bc209afe5a66b1";

    private <T> T createService(Class<T> service) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }
}
