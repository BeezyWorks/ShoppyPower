package com.mattaniahbeezy.shoppylist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backend.ProductCallback;
import com.backend.models.Product;
import com.mattaniahbeezy.shoppylist.R;
import com.mattaniahbeezy.shoppylist.adapters.ProductsAdapter;
import com.mattaniahbeezy.shoppylist.utilities.DataManager;
import com.mattaniahbeezy.shoppylist.viewholders.ProductViewHolder;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ProductCallback {
    RecyclerView productsRecyclerView;
    ProductsAdapter adapter;
    ProductViewHolder.OnProductSelected productSelected;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof ProductViewHolder.OnProductSelected)) {
            throw new IllegalArgumentException("Parent Activity must implement OnProductSelected");
        }
        this.productSelected = (ProductViewHolder.OnProductSelected) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.productSelected = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.adapter != null && this.productsRecyclerView != null) {
            this.productsRecyclerView.setAdapter(this.adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.productsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DataManager.getInstance().getProducts(this);
    }

    @Override
    public void productsReady(boolean isSuccess, List<Product> products) {
        if (this.adapter == null) {
            this.adapter = new ProductsAdapter(products, this.productSelected);
            this.productsRecyclerView.setAdapter(adapter);
        } else {
            this.adapter.setProducts(products);
        }
    }
}
