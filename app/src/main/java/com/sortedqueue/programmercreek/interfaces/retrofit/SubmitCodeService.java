package com.sortedqueue.programmercreek.interfaces.retrofit;

import com.sortedqueue.programmercreek.database.firebase.CodeOutputResponse;
import com.sortedqueue.programmercreek.database.firebase.IdResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alok Omkar on 2017-03-13.
 */

public interface SubmitCodeService {

    @Headers("Content-Type: application/json")
    @POST("/api/v3/submissions")
    Call<IdResponse> postCode(@Body HashMap<String, String> codeMap, @Query("access_token") String accessToken );

    @GET("/api/v3/submissions/{id}")
    Call<CodeOutputResponse> getOutput(@Path("id") Integer submissionId,
                                       @Query("access_token") String accessToken,
                                       @Query("withOutput") boolean withOutput,
                                       @Query("withSource") boolean withSource,
                                       @Query("withStderr") boolean withStderr,
                                       @Query("withCmpinfo") boolean withCmpinfo);
}
