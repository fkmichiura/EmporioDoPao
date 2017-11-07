package com.project.emporiodopao.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fábio Michiura on 20/04/2017.
 */

public class ProductOrder implements Serializable{

    @SerializedName("idLoja")
    private int idShop = 1; //Valor correspondente ao Empório do Pão
    @SerializedName("nome")
    private String contactName = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("telefone")
    private String phone = "";
    @SerializedName("horario")
    private String orderDate = "";
    private String orderTime = "";
    private String invOrderDate = "";
    @SerializedName("valorPedido")
    private float totalValor;

    @SerializedName("produtos")
    private ArrayList<Item> selProducts;

    public ProductOrder() {
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getInvOrderDate() {
        return invOrderDate;
    }

    public void setInvOrderDate(String invOrderDate) {
        this.invOrderDate = invOrderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public ArrayList<Item> getSelProducts() {
        return selProducts;
    }

    public void setSelProducts(ArrayList<Item> selProducts) {
        this.selProducts = selProducts;
    }

    public float getTotalValor() {

        for(Item i : getSelProducts()){
            totalValor += i.getSubTotalValue();
        }
        return totalValor;
    }

}
