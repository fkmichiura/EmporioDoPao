package com.project.emporiodopao.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.emporiodopao.models.ProductOrder;
import com.project.emporiodopao.R;
import com.project.emporiodopao.adapters.ProductAdapter;
import com.project.emporiodopao.models.Item;
import com.project.emporiodopao.models.ItemsCatalog;
import com.project.emporiodopao.models.ItemsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    private ProductOrder productOrder = new ProductOrder();
    private ArrayList<Item> products = new ArrayList<>();
    private ArrayList<Item> selectedProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.items_list);

        //Consumo de API com Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ItemsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ItemsService service = retrofit.create(ItemsService.class);
        Call<ItemsCatalog> requestCatalog = service.getItemsList("listaProdutos", 1);

        requestCatalog.enqueue(new Callback<ItemsCatalog>() {
            @Override
            public void onResponse(Call<ItemsCatalog> call, Response<ItemsCatalog> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "Erro: " + response.code());
                }
                else{
                    //Armazena o conteúdo consumido pela API
                    ItemsCatalog catalog = response.body();

                    //Atribui ao ArrayList os dados consumidos pela API
                    products = catalog.retorno;

                    //Atribui o valor inicial aos itens do ArrayList
                    for(Item i : products){
                        i.setProdQty(0);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);

                    ProductAdapter adapter = new ProductAdapter(products, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ItemsCatalog> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });

        //.................
        //Botões de categorias - move para a posição da categoria selecionada
        //.................

        Button cakesBtn = (Button)findViewById(R.id.cake_btn);
        cakesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        Button appetBtn = (Button)findViewById(R.id.appet_btn);
        appetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(18);
            }
        });

        Button sandBtn = (Button)findViewById(R.id.sand_btn);
        sandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(40);
            }
        });

        //.................
        //Botões DateActivity - chama a Activity da data do pedido
        //.................
        Button menuBtn = (Button)findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DateActivity.class);
                productOrder.setSelProducts(selectedProducts);
                //Enviar o objeto ProductOrder para a próxima Activity
                intent.putExtra("Produtos", productOrder);
                startActivity(intent);
            }
        });

    }

    //Adiciona item na lista
    public void addProducts(Item product){
        selectedProducts.add(product);
    }

    //Remove item na lista
    public void removeProducts(Item product){
        selectedProducts.remove(product);
    }
}