package com.mattaniahbeezy.shoppylist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.backend.models.Product;
import com.mattaniahbeezy.shoppylist.viewholders.ProductViewHolder;

import java.util.List;

/**
 * Created by Beezy Works Studios on 8/22/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> products;
    private ProductViewHolder.OnProductSelected productSelected;

    public ProductsAdapter(List<Product> products, ProductViewHolder.OnProductSelected productSelected) {
        this.products = products;
        this.productSelected = productSelected;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent, this.productSelected);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.setImageViewUrl(product.getImageUrl());
        holder.setProductName(product.getName());
        holder.setProductUPC(product.getUpc());
        holder.setProductPrice(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
