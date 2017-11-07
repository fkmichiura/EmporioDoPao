package com.project.emporiodopao.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.emporiodopao.activities.MainActivity;
import com.project.emporiodopao.models.Item;
import com.project.emporiodopao.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Fábio Michiura on 20/04/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private int value = 0;
    private int selectedPos = 0;
    private Context context;
    private ArrayList<Item> prodData = new ArrayList<>();
    private MainActivity mainActivity;

    private SparseBooleanArray selectedItem = new SparseBooleanArray();

    public ProductAdapter(ArrayList<Item> data, Context ctx){

        this.prodData = data;
        this.context = ctx;
        this.mainActivity = (MainActivity)ctx;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //Verifica se o item na posição está ou não selecionado
        holder.itemView.setSelected(selectedItem.get(position));

        Item current = prodData.get(position);

        //Atribui os textos armazenados nas TextViews
        holder.prodName.setText(current.getProdName());
        holder.prodQt.setText(String.valueOf(current.getProdQty()));
        holder.prodDescr.setText("R$ " + String.valueOf(current.getValue()) + 0);

        //Verifica a categoria de cada item para nomeação da CardView
        switch (prodData.get(position).getIdCategory()){
            case 1:
                holder.prodCat.setText("Bolos e Doces");
                break;
            case 2:
                holder.prodCat.setText("Mini Salgados");
                break;
            case 3:
                holder.prodCat.setText("Mini Lanches");
        }

    }

    @Override
    public int getItemCount() {
        return prodData.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public LinearLayout txtLayout;
        public TextView prodName;
        public TextView prodDescr;
        public TextView prodCat;
        public TextView prodQt;
        public Button decr_btn;
        public Button incr_btn;
        public CardView cardView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            prodName = (TextView) itemView.findViewById(R.id.prodName);
            prodDescr = (TextView) itemView.findViewById(R.id.prodDescr);
            prodCat = (TextView) itemView.findViewById(R.id.itemCategory);
            prodQt = (TextView) itemView.findViewById(R.id.quant_txt);
            decr_btn = (Button) itemView.findViewById(R.id.decrem_btn);
            incr_btn = (Button) itemView.findViewById(R.id.incr_btn);
            txtLayout = (LinearLayout)itemView.findViewById(R.id.txtLayout);
            cardView = (CardView)itemView.findViewById(R.id.card_item);

            //Evento de clique dos itens na RecyclerView
            txtLayout.setOnClickListener(this);
            decr_btn.setOnClickListener(this);
            incr_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.txtLayout && itemView.isSelected()){
                //Remove o item selecionado do ArrayList
                mainActivity.removeProducts(prodData.get(getAdapterPosition()));

                prodData.get(getAdapterPosition()).setProdQty(0);

                //Deleta o item da lista de verificação dos itens selecionados
                selectedItem.delete(getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            }

            else if(v.getId() == R.id.txtLayout){
                //Armazena o item selecionado no ArrayList
                mainActivity.addProducts(prodData.get(getAdapterPosition()));

                prodData.get(getAdapterPosition()).setProdQty(1);

                //Adiciona o item na lista de verificação dos itens selecionados
                selectedItem.put(getAdapterPosition(), true);
                notifyItemChanged(getAdapterPosition());
            }

            //Verifica se o item está selecionado
            if(selectedItem.get(getAdapterPosition()) == true){

                switch (v.getId()) {
                    case R.id.decrem_btn:
                        //Decremento do valor relativo a quantidade de itens escolhida
                        value = prodData.get(getAdapterPosition()).getProdQty();

                        if(value <= 1){
                            value = 1;
                        }
                        else{
                            value--;
                        }
                        prodData.get(getAdapterPosition()).setProdQty(value);
                        prodQt.setText(String.valueOf(value));
                        break;

                    case R.id.incr_btn:
                        //Conversão em valor inteiro para operação de incremento
                        value = prodData.get(getAdapterPosition()).getProdQty();

                        value++;
                        prodData.get(getAdapterPosition()).setProdQty(value);
                        Log.i(TAG, "Posicao Adapter: " + getAdapterPosition());
                        Log.i(TAG, "Sel Posicao: " + selectedPos);

                        prodQt.setText(String.valueOf(value));
                        break;

                }

            }
        }
    }
}
