package com.backend;

import com.backend.models.Product;
import com.backend.rest.ProductsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Beezy Works Studios on 8/21/2017.
 */

public class ShoppyController implements Callback<List<Product>> {

    private static final String BASE_URL = "https://s3.amazonaws.com/storepower/";
    private static final ShoppyController instance = new ShoppyController();

    private ProductCallback productCallback;

    public static ShoppyController instance() {
        return instance;
    }

    private ShoppyController() {
    }

    public void setProductCallback(ProductCallback productCallback) {
        this.productCallback = productCallback;
    }

    public void getProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductsService productsService = retrofit.create(ProductsService.class);

        Call<List<Product>> call = productsService.getProducts();
        call.enqueue(this);
    }

    public void reduceProductQTY(Product product, OnProductReduced productReduced) {
        // do something in background to set product over rest
        productReduced.productReduced(product, product.reduceQty());
    }

    @Override
    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
        if (this.productCallback != null) {
            List<Product> products = response.body();
            for (Product product : products) {
                System.out.println(product.getName());
            }
            this.productCallback.productsReady(response.isSuccessful(), products);
        }
    }

    @Override
    public void onFailure(Call<List<Product>> call, Throwable throwable) {
        throwable.printStackTrace();
    }

    public interface OnProductReduced {
        void productReduced(Product product, boolean success);
    }
}
