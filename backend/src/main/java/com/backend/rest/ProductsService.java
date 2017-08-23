package com.backend.rest;

import com.backend.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Beezy Works Studios on 8/21/2017.
 */

public interface ProductsService {
    @GET("job_application_test.json")
    Call<List<Product>> getProducts();
}
