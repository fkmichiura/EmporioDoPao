package com.project.emporiodopao.models;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by DarKnigHt on 26/04/2017.
 */

public interface OrderService {

    String BASE_URL = "http://beta.momentochef.com.br/appPedidos/";

    @FormUrlEncoded
    @POST("index.php")
    Call<OrderCatalog> sendMailData(
            @Field("acao") String action,
            @Field("idLoja") int id,
            @Field("nome") String clientName,
            @Field("email") String clientMail,
            @Field("telefone") String clientPhone,
            @Field("horario") String orderTime,
            @Field("valorPedido") float totalValue,
            @Field("produtos[]") ArrayList<JsonObject> products);

}
