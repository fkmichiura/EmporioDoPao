package com.project.emporiodopao.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.project.emporiodopao.R;
import com.project.emporiodopao.adapters.OrderListAdapter;
import com.project.emporiodopao.models.Item;
import com.project.emporiodopao.models.OrderCatalog;
import com.project.emporiodopao.models.OrderService;
import com.project.emporiodopao.models.ProductOrder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {

    private ProductOrder productOrder;
    private ArrayList<Item> productsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String timePicker;

    private static int TIME_OUT = 2000; //Tempo de espera para execução de uma ação

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = (RecyclerView) findViewById(R.id.orders_list);

        //Botão para retornar à Activity pai
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Atribui à variável productOrder o conteúdo da Activity anterior
        productOrder = (ProductOrder) getIntent().getSerializableExtra("Produtos");

        //ArrayList dos itens selecionados
        productsList = productOrder.getSelProducts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        OrderListAdapter adapter = new OrderListAdapter(productsList, OrderActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //Associa as variáveis aos seus respectivos TextViews
        TextView cName = (TextView)findViewById(R.id.tvName);
        TextView cMail = (TextView)findViewById(R.id.tvMail);
        TextView cPhone = (TextView)findViewById(R.id.tvPhone);

        TextView orderDate = (TextView)findViewById(R.id.tvDate);
        TextView orderTime = (TextView)findViewById(R.id.tvTime);

        TextView totalValue = (TextView)findViewById(R.id.totalValue);

        //Atribui os valores do objeto ProductOrder às TextViews
        cName.setText(productOrder.getContactName());
        cMail.setText(productOrder.getEmail());
        cPhone.setText(productOrder.getPhone());

        orderDate.setText(productOrder.getOrderDate());
        orderTime.setText(productOrder.getOrderTime());

        totalValue.setText(String.valueOf("Total do pedido: " + "R$ " + productOrder.getTotalValor() + 0));

        final ArrayList<JsonObject> produtos = convertJSON(productsList);

        //Armazenar o horário selecionado em uma lista
        Button sendBtn = (Button)findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //Atribuição de segundos para envio por POST
            timePicker = productOrder.getInvOrderDate() + " " + productOrder.getOrderTime() + ":00";
            productOrder.setOrderDate(timePicker);

            //Consumo de API com Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(OrderService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OrderService service = retrofit.create(OrderService.class);

                for (int i = 0; i < produtos.size(); i++){

                    Log.i("TAG", "Json: " + produtos.get(i).toString());
                }


            //Chamada do POST
            Call<OrderCatalog> requestCatalog = service.sendMailData(
                    "enviaPedido",
                    productOrder.getIdShop(),
                    productOrder.getContactName(),
                    productOrder.getEmail(),
                    productOrder.getPhone(),
                    productOrder.getOrderDate(),
                    productOrder.getTotalValor(),
                    produtos);

            requestCatalog.enqueue(new Callback<OrderCatalog>() {
                @Override
                public void onResponse(Call<OrderCatalog> call, Response<OrderCatalog> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(OrderActivity.this, "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        //Envio dos dados para a API
                        Toast.makeText(OrderActivity.this, "Envio realizado com sucesso. \n Obrigado pela preferência", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderCatalog> call, Throwable t) {

                    Toast.makeText(OrderActivity.this, "Falha ao enviar o pedido", Toast.LENGTH_LONG).show();
                }
            });

            //Chama a Activity Título após 2 segundos
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(OrderActivity.this, TitleActivity.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
            }
        });
    }

    //Salva o contexto a ser utilizado na Activity pai
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("Produtos", productOrder);
            setResult(RESULT_OK, intent);
            finish();
            onBackPressed();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método de conversão de ArrayList para JSONArray de JSONObjects
    public ArrayList<JsonObject> convertJSON(ArrayList<Item> itemArrayList){

        ArrayList<JsonObject> jsonArray = new ArrayList<>();

        for (int i = 0; i < itemArrayList.size(); i++) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", itemArrayList.get(i).getId());
            jsonObject.addProperty("quantidade", itemArrayList.get(i).getProdQty());
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }
}
