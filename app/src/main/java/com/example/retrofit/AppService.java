
package com.example.retrofit;
import com.example.retrofit.Model.Get;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppService {


    @GET("posts")
    Call<List<Get>> getData();
}
