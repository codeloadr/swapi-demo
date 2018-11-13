package com.nsoni.starwars;

import com.nsoni.starwars.data.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StarWarsClient {

    @GET("/api/people/")
    Call<Result> lookupCharacterNames(@Query("search") String searchWord);

}
