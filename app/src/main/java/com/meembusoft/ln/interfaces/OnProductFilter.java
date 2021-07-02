package com.meembusoft.ln.interfaces;

import androidx.collection.ArrayMap;

import com.meembusoft.ln.model.colormatchtab.Product;

import java.util.List;

public interface OnProductFilter {
    void findFilterKeys(List<Product> productList);

    ArrayMap<String, List<String>> getFilterKeys();

    void setSelectedFilterKeys(ArrayMap<String, List<String>> selectedFilterKeys);

    ArrayMap<String, List<String>> getSelectedFilterKeys();
}