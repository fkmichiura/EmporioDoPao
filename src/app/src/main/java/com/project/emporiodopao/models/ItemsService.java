package com.project.emporiodopao.models;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Fábio Michiura on 17/04/2017.
 */

public interface ItemsService {

    String BASE_URL = "http://beta.momentochef.com.br/appPedidos/";

    @FormUrlEncoded
    @POST("index.php")
    Call<ItemsCatalog> getItemsList(
            @Field("acao") String action,
            @Field("idLoja") int id);
}
