package com.mattaniahbeezy.shoppylist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattaniahbeezy.shoppylist.R;
import com.mattaniahbeezy.shoppylist.utilities.GlideApp;

public class ProductDetailsFragment extends Fragment {
    private OnAddProduct onAddProduct;

    private String productImageUrl, productName, productUPC, productPrice;
    private float productQty;

    ImageView productImageView;
    TextView productNameView, productUPCView, productPriceView, productQtyView;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance() {
        return new ProductDetailsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof OnAddProduct)) {
            throw new IllegalArgumentException("Parent Activity must implement OnAddProduct");
        }
        this.onAddProduct = (OnAddProduct) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onAddProduct = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productImageView = (ImageView) view.findViewById(R.id.productImage);
        this.productNameView = (TextView) view.findViewById(R.id.productName);
        this.productUPCView = (TextView) view.findViewById(R.id.productUPC);
        this.productQtyView = (TextView) view.findViewById(R.id.productQty);
        this.productPriceView = (TextView) view.findViewById(R.id.productPrice);

        view.findViewById(R.id.addToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailsFragment.this.onAddProduct.addProduct(ProductDetailsFragment.this.productUPC);
            }
        });

        setProductImageUrl(this.productImageUrl);
        setProductName(this.productName);
        setProductUPC(this.productUPC);
        setProductPrice(this.productPrice);
        setProductQty(this.productQty);
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
        if(!isAdded() || this.productImageView == null || productImageUrl == null) return;
        GlideApp.with(getContext())
                .load(productImageUrl)
                .placeholder(R.drawable.placeholder)
                .into(this.productImageView);
    }

    public void setProductName(String productName) {
        this.productName = productName;
        if(!isAdded() || this.productNameView == null || productName == null) return;
        this.productNameView.setText(productName);
    }

    public void setProductUPC(String productUPC) {
        this.productUPC = productUPC;
        if(!isAdded() || this.productUPCView == null || productUPC == null) return;
        this.productUPCView.setText(productUPC);
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
        if(!isAdded() || this.productPriceView == null || productPrice == null) return;
        this.productPriceView.setText(productPrice);
    }

    public void setProductQty(float productQty) {
        this.productQty = productQty;
        if(!isAdded() || this.productQtyView == null) return;
        this.productQtyView.setText(String.valueOf(productQty));
    }

    public String getProductUPC(){
        return this.productUPC;
    }

    public interface OnAddProduct{
        void addProduct(String productUPC);
    }
}
