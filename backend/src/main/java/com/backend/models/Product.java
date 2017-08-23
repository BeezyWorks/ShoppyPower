package com.backend.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Beezy Works Studios on 8/21/2017.
 */

public class Product {
    private String upc;
    private String name;
    private float qty;
    @SerializedName("is_priced_by_weight")
    private boolean isPriceByWeight;
    @SerializedName("priced_by_weight_uom")
    private String pricedByWeightUOM;
    @SerializedName("weight_per_each")
    private String weightPerEach;
    private String eaches;
    private String price;
    @SerializedName("image_url")
    private String imageUrl;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public boolean isPriceByWeight() {
        return isPriceByWeight;
    }

    public void setPriceByWeight(boolean priceByWeight) {
        isPriceByWeight = priceByWeight;
    }

    public String getPricedByWeightUOM() {
        return pricedByWeightUOM;
    }

    public void setPricedByWeightUOM(String pricedByWeightUOM) {
        this.pricedByWeightUOM = pricedByWeightUOM;
    }

    public String getWeightPerEach() {
        return weightPerEach;
    }

    public void setWeightPerEach(String weightPerEach) {
        this.weightPerEach = weightPerEach;
    }

    public String getEaches() {
        return eaches;
    }

    public void setEaches(String eaches) {
        this.eaches = eaches;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean reduceQty() {
        if (this.qty >= 0.5) {
            this.qty -= 0.5;
            return true;
        }
        return false;
    }
}