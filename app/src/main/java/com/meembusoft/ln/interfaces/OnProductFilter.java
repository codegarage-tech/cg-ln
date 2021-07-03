package com.meembusoft.ln.interfaces;

import androidx.collection.ArrayMap;

import com.meembusoft.ln.model.colormatchtab.Product;

import java.util.List;

public interface OnProductFilter {
    void findFilterKeys(List<Product> productList);

    ArrayMap<String, List<String>> getFilterKeys();

    void setAppliedFilterKeys(ArrayMap<String, List<String>> appliedFilterKeys);

    ArrayMap<String, List<String>> getAppliedFilterKeys();
}