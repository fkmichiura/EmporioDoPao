package com.project.emporiodopao.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.project.emporiodopao.R;
import com.project.emporiodopao.models.ProductOrder;

import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    private ProductOrder productOrder;
    private TimePicker timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        //Botão para retornar à Activity pai
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Atribui à variável productOrder o conteúdo da Activity anterior
        productOrder = (ProductOrder) getIntent().getSerializableExtra("Produtos");

        //Atribuição do relógio 24h para o TimePicker
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        //Armazenar o horário selecionado em uma lista
        Button userBtn = (Button)findViewById(R.id.time_btn);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                //Atribui o horário capturado pelo TimePicker ao objeto ProductOrder
                productOrder.setOrderTime(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

                //Enviar o objeto ProductOrder para a próxima Activity
                Intent intent = new Intent(TimeActivity.this, ContactActivity.class);
                intent.putExtra("Produtos", productOrder);
                startActivityForResult(intent, 2);
            }
        });
    }

    //Recupera o objeto da Activity filho para utilização
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                ProductOrder productOrder = (ProductOrder)data.getSerializableExtra("Produtos");
            }
        }
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

}
