package com.sortedqueue.programmercreek.interfaces.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Alok Omkar on 2017-03-13.
 */

public interface SubmitCodeService {

    @FormUrlEncoded
    @POST("/submissions")
    Call<ResponseBody> postCode( @Field("language") int language,
                                 @Field("sourceCode") )
}
