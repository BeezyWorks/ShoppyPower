package com.backend;

import com.backend.models.Product;

import java.util.List;

/**
 * Created by Beezy Works Studios on 8/21/2017.
 */

public interface ProductCallback {
    void productsReady(boolean isSuccess, List<Product> products);
}
