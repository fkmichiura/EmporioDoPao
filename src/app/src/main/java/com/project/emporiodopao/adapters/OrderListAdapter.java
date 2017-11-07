package com.project.emporiodopao.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.emporiodopao.models.Item;
import com.project.emporiodopao.R;

import java.util.ArrayList;

/**
 * Created by Fábio Michiura on 20/04/2017.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>{

    private ArrayList<Item> itemData = new ArrayList<>();
    private Context context;

    public OrderListAdapter(ArrayList<Item> data, Context context){

        this.itemData = data;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Item current = itemData.get(position);

        //Atribui os textos armazenados nas TextViews
        holder.itemName.setText(current.getProdName());
        holder.itemDescr.setText("Valor unitário: " + "R$ " + String.valueOf(current.getValue()) + 0);
        holder.itemQty.setText("Quantidade: " + String.valueOf(current.getProdQty()));
        holder.itemTotal.setText("Valor total do produto: " + "R$ " + current.getSubTotalValue() + 0);

        //Verifica a categoria de cada item para nomeação da CardView
        switch (itemData.get(position).getIdCategory()){
            case 1:
                holder.itemCateg.setText("Bolos e Doces");
                break;
            case 2:
                holder.itemCateg.setText("Mini Salgados");
                break;
            case 3:
                holder.itemCateg.setText("Mini Lanches");


        }

    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName;
        public TextView itemDescr;
        public TextView itemCateg;
        public TextView itemQty;
        public TextView itemTotal;

        public OrderViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.prodName);
            itemDescr = (TextView) itemView.findViewById(R.id.prodDescr);
            itemCateg = (TextView) itemView.findViewById(R.id.prodCateg);
            itemQty = (TextView) itemView.findViewById(R.id.prodQty);
            itemTotal = (TextView) itemView.findViewById(R.id.prodTotal);
        }

    }

}
