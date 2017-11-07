package com.project.emporiodopao.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fábio Michiura on 20/04/2017.
 */

public class Item implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("idCategoria")
    private int idCategory;
    @SerializedName("nome")
    private String prodName;
    @SerializedName("descricao")
    private String prodDescr;
    @SerializedName("valor")
    private float value;
    @SerializedName("quantidade")
    private int prodQty = 0;

    public Item(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getProdQty() {
        return prodQty;
    }

    public void setProdQty(int prodQty) {
        this.prodQty = prodQty;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDescr() {
        return prodDescr;
    }

    public void setProdDescr(String prodDescr) {
        this.prodDescr = prodDescr;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getSubTotalValue() {
        return (float)prodQty*value;
    }

}
