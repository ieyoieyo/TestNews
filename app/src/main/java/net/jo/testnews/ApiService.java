package net.jo.testnews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Yusuf on 09.10.2016.
 */

public interface ApiService {

    @GET("interview_get_vector.json")
    Call<JsonBean> getData();

}
