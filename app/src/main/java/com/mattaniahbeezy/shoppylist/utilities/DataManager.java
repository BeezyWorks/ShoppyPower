package com.mattaniahbeezy.shoppylist.utilities;

import android.content.Context;
import android.support.annotation.Nullable;

import com.backend.ProductCallback;
import com.backend.ShoppyController;
import com.backend.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Beezy Works Studios on 8/23/2017.
 */

public class DataManager implements ProductCallback {
    private static final DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private HashMap<String, Product> cachedProducts = new HashMap<>();
    private ProductCallback productCallback;

    private DataManager() {
    }

    public void setContext(Context context) {
        SharedPreferenceManager.getInstance().setContext(context);
    }

    public void getProducts(ProductCallback productCallback) {
        this.productCallback = productCallback;
        ShoppyController.instance().setProductCallback(this);
        productCallback.productsReady(true, new ArrayList<>(getCachedProducts().values()));
        ShoppyController.instance().getProducts();
    }

    private HashMap<String, Product> getCachedProducts() {
        if (this.cachedProducts.isEmpty()) {
            for (Product product : getStoredProducts()) {
                this.cachedProducts.put(product.getUpc(), product);
            }
        }
        return this.cachedProducts;
    }

    private List<Product> getStoredProducts() {
        String rawJson = SharedPreferenceManager.getInstance().getString(SharedPreferenceManager.CACHED_PRODUCTS_KEY, null);
        Type listType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        return new Gson().fromJson(rawJson, listType);
    }

    private void saveProducts() {
        List<Product> products = new ArrayList<>(getCachedProducts().values());
        SharedPreferenceManager.getInstance().setString(SharedPreferenceManager.CACHED_PRODUCTS_KEY, new Gson().toJson(products));
    }

    public void addProductToCart(final String productUPC, final OnAddToCart addToCart) {
        Product product = getProductByUPC(productUPC);
        ShoppyController.instance().reduceProductQTY(product, new ShoppyController.OnProductReduced() {
            @Override
            public void productReduced(Product product, boolean success) {
                DataManager.this.cachedProducts.put(product.getUpc(), product);
                saveProducts();
                if(DataManager.this.productCallback!=null){
                    DataManager.this.productCallback.productsReady(true, new ArrayList<Product>(getCachedProducts().values()));
                }
                addToCart.addedToCart(productUPC);
            }
        });
    }

    public
    @Nullable
    Product getProductByUPC(String upc) {
        //TODO: this should be done with an actual SQLite ... once there's a SQLite db
        if (getCachedProducts().containsKey(upc)) {
            return getCachedProducts().get(upc);
        }
        return null;
    }

    @Override
    public void productsReady(boolean isSuccess, List<Product> products) {
        SharedPreferenceManager.getInstance().setString(SharedPreferenceManager.CACHED_PRODUCTS_KEY, new Gson().toJson(products));
        getCachedProducts().clear();
        if (this.productCallback != null) {
            this.productCallback.productsReady(isSuccess, products);
        }
    }

    public interface OnAddToCart{
        void addedToCart(String productUPC);
    }
}
