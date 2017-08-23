package com.mattaniahbeezy.shoppylist.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattaniahbeezy.shoppylist.R;
import com.mattaniahbeezy.shoppylist.utilities.GlideApp;

import static com.mattaniahbeezy.shoppylist.R.id.productImage;

/**
 * Created by Beezy Works Studios on 8/22/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private static final int LAYOUT = R.layout.product_view_holder_layout;

    private Context context;
    private ImageView imageView;
    private TextView productNameView, productUPCView, productPriceView;

    public static ProductViewHolder create(ViewGroup parentView, OnProductSelected productSelected) {
        LayoutInflater inflater = LayoutInflater.from(parentView.getContext());
        return new ProductViewHolder(inflater.inflate(LAYOUT, parentView, false), productSelected);
    }

    private ProductViewHolder(View itemView, final OnProductSelected productSelected) {
        super(itemView);
        this.context = itemView.getContext();
        this.imageView = (ImageView) itemView.findViewById(productImage);
        this.productNameView = (TextView) itemView.findViewById(R.id.productName);
        this.productUPCView = (TextView) itemView.findViewById(R.id.productUPC);
        this.productPriceView = (TextView) itemView.findViewById(R.id.productPrice);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productSelected.productSelected(ProductViewHolder.this.productUPCView.getText().toString());
            }
        });
    }

    public void setImageViewUrl(String url) {
        GlideApp.with(this.context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    public void setProductName(String productName) {
        this.productNameView.setText(productName);
    }

    public void setProductUPC(String productUPC) {
        this.productUPCView.setText(productUPC);
    }

    public void setProductPrice(String productPrice) {
        this.productPriceView.setText(productPrice);
    }

    public interface OnProductSelected {
        void productSelected(String productUPC);
    }
}
