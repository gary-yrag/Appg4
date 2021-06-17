package com.example.inicio.io;
import com.example.inicio.model.muestra;
import com.example.inicio.model.muestraEnviar;
import com.example.inicio.model.response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface muestraapiService {
    @GET("muestra/listarmuestraspadre/{hash}")
    Call<ArrayList<muestra>> getMuestras(@Path("hash") String hash);

    //@Headers("Content-Type: application/json")
    @POST("muestra/actualizarmuestras")
    Call <response> setMuestras(@Body ArrayList<muestraEnviar> body);

}
