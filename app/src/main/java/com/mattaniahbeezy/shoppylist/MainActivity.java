package com.mattaniahbeezy.shoppylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.backend.models.Product;
import com.mattaniahbeezy.shoppylist.fragments.MainActivityFragment;
import com.mattaniahbeezy.shoppylist.fragments.ProductDetailsFragment;
import com.mattaniahbeezy.shoppylist.utilities.DataManager;
import com.mattaniahbeezy.shoppylist.viewholders.ProductViewHolder;

public class MainActivity extends AppCompatActivity implements ProductViewHolder.OnProductSelected, ProductDetailsFragment.OnAddProduct, DataManager.OnAddToCart {
    ProductDetailsFragment productDetailsFragment;
    private boolean inProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.getInstance().setContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainActivityFragment()).addToBackStack(null).commit();
    }


    @Override
    public void productSelected(String productUPC) {
        Product product = DataManager.getInstance().getProductByUPC(productUPC);
        if (product == null) {
            Toast.makeText(this, "Product not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.productDetailsFragment == null) {
            this.productDetailsFragment = ProductDetailsFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, this.productDetailsFragment).addToBackStack(null).commit();
        this.inProductDetails = true;
        this.productDetailsFragment.setProductImageUrl(product.getImageUrl());
        this.productDetailsFragment.setProductName(product.getName());
        this.productDetailsFragment.setProductPrice(product.getPrice());
        this.productDetailsFragment.setProductUPC(product.getUpc());
        this.productDetailsFragment.setProductQty(product.getQty());
    }

    @Override
    public void onBackPressed() {
        if(this.inProductDetails){
            getSupportFragmentManager().popBackStack();
            this.inProductDetails = false;
        }
        else{
            finish();
        }
    }

    @Override
    public void addProduct(String productUPC) {
        DataManager.getInstance().addProductToCart(productUPC, this);
    }

    @Override
    public void addedToCart(String productUPC) {
        if(this.inProductDetails & this.productDetailsFragment.getProductUPC().equals(productUPC)){
            this.productDetailsFragment.setProductQty(DataManager.getInstance().getProductByUPC(productUPC).getQty());
        }
    }
}
