package io.innovate.innovate.Util;

import android.util.Log;

import com.squareup.otto.Produce;

import io.innovate.innovate.Model.AppConstant;
import io.innovate.innovate.Model.ErrorEvent;
import io.innovate.innovate.Model.ServerEvent;
import io.innovate.innovate.Model.ServerResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NottyNerd on 06/02/2017.
 */
public class Transport {

    public void makePost() {

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.URL)
                .build();
        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.post();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                BProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e("LOGGER", "Success");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

    public void makeGet() {
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.URL)
                .build();

        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call = service.get();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                BProvider.getInstance().post(new ServerEvent(response.body()));
                Log.e("LOGGER", "Success");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }


    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }



}