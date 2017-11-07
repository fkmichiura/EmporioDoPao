package com.project.emporiodopao.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.emporiodopao.R;
import com.project.emporiodopao.models.ProductOrder;

public class ContactActivity extends AppCompatActivity {

    private ProductOrder productOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Botão para retornar à Activity pai
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Atribui à variável productOrder o conteúdo da Activity anterior
        productOrder = (ProductOrder) getIntent().getSerializableExtra("Produtos");

        //Associa as variáveis aos seus respectivos EditTexts
        final EditText contactName = (EditText)findViewById(R.id.nameTxt);
        final EditText contactMail = (EditText)findViewById(R.id.mailTxt);
        final EditText contactPhone = (EditText)findViewById(R.id.phoneNumb);

        //Armazenar o horário selecionado em uma lista
        Button orderBtn = (Button)findViewById(R.id.time_btn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, OrderActivity.class);

                String cName = contactName.getText().toString();
                String cMail = contactMail.getText().toString();
                String cPhone = contactPhone.getText().toString();

                //Atribui os campos de texto ao objeto ProductOrder para utilização da próxima Activity
                productOrder.setContactName(cName);
                productOrder.setEmail(cMail);
                productOrder.setPhone(cPhone);

                if(cName.matches("") || cMail.matches("") || cPhone.matches("")){
                    Toast.makeText(ContactActivity.this, "Favor, preencher todos os campos conforme solicitado",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    //Enviar o objeto ProductOrder para a próxima Activity
                    intent.putExtra("Produtos", productOrder);
                    startActivityForResult(intent, 3);
                }
            }
        });
    }

    //Recupera o objeto da Activity filho para utilização
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3){
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
