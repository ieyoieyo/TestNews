package net.jo.testnews;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Model {

    private ApiService myApi;

    public Model() {
        // Creating an object of our api interface
        myApi = RetroClient.getApiService();
    }

    // Get user-list
    void getData(final ICallback<JsonBean> callback) {

        // Calling JSON
        Call<JsonBean> call = myApi.getData();

        // Enqueue Callback will be call when get response...
        call.enqueue(new Callback<JsonBean>() {
            @Override
            public void onResponse(Call<JsonBean> call, retrofit2.Response<JsonBean> response) {

                if (response.isSuccessful()) {

                    callback.onSuccess(response.body());

                } else {

                    callback.onError("Something work wrong");
                }

            }

            @Override
            public void onFailure(Call<JsonBean> call, Throwable t) {

                Log.e("__", t.getMessage());
                callback.onFailure("On Failure, " + t.getMessage());
            }
        });

    }

}
