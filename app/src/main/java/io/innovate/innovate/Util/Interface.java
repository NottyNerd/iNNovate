package io.innovate.innovate.Util;

import io.innovate.innovate.Model.AppConstant;
import io.innovate.innovate.Model.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by NottyNerd on 06/02/2017.
 */
public interface Interface {

    //This method is used for "POST"
    @FormUrlEncoded
    @POST(AppConstant.apiUrl)
    Call<ServerResponse> post(
//            @Field("method") String method,
//            @Field("username") String username,
//            @Field("password") String password
    );

    //This method is used for "GET"
    @GET(AppConstant.apiUrl)
    Call<ServerResponse> get(
//            @Query("method") String method,
//            @Query("username") String username,
//            @Query("password") String password
    );

}