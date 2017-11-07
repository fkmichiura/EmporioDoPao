package com.project.emporiodopao.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.project.emporiodopao.models.ProductOrder;
import com.project.emporiodopao.R;

import java.util.Locale;

public class DateActivity extends AppCompatActivity{

    private ProductOrder productOrder;
    private DatePicker datePicker;
    private String stringDate;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        //Atribui à variável productOrder o conteúdo da Activity anterior
        productOrder = (ProductOrder) getIntent().getSerializableExtra("Produtos");
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        //Botão para retornar à Activity pai
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Armazenar a data selecionada em uma lista
        Button userBtn = (Button)findViewById(R.id.datepick_btn);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Captura os valores referentes à data do pedido
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                stringDate = String.format(Locale.getDefault(), "%02d-%02d-%02d", day, month, year);

                //Atribui a data capturada pelo DatePicker ao objeto ProductOrder
                productOrder.setOrderDate(stringDate);
                productOrder.setInvOrderDate(String.format(Locale.getDefault(), "%02d-%02d-%02d", year, month, day));

                //Enviar o objeto ProductOrder para a próxima Activity
                Intent intent = new Intent(DateActivity.this, TimeActivity.class);
                intent.putExtra("Produtos", productOrder);
                startActivityForResult(intent, 1);
            }
        });
    }

    //Recupera o objeto da Activity filho para utilização
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                ProductOrder productOrder = (ProductOrder)data.getSerializableExtra("Produtos");
            }
        }
    }
}
