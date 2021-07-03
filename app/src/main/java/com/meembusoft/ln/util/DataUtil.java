package com.meembusoft.ln.util;

import android.content.Context;
import android.text.TextUtils;

import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.meembusoft.ln.model.colormatchtab.response.ResponseCategory;
import com.meembusoft.ln.model.colormatchtab.response.ResponseProduct;
import com.meembusoft.retrofitmanager.APIResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class DataUtil {

    private static String TAG = DataUtil.class.getSimpleName();

    // Asset file path
    public static final String ASSET_API_RESPONSE_BASE_PATH = "api_responses/";
    public static final String ASSET_FILE_NAME_CATEGORY_WITH_SUBCATEGORY = "category_with_subcategory.json";
    //    public static final String ASSET_FILE_NAME_PRODUCT_RICE = "product_list_rice.json";
    public static final String ASSET_FILE_PATH_CATEGORY_WITH_SUBCATEGORY = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_CATEGORY_WITH_SUBCATEGORY;
//    public static final String ASSET_FILE_PATH_PRODUCT = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_PRODUCT;

    public static List<Category> getAllCategoriesWithSubcategories(Context context) {
        List<Category> categories = new ArrayList<>();
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, ASSET_FILE_PATH_CATEGORY_WITH_SUBCATEGORY);
        Logger.d(TAG, TAG + ">>getAllCategories>>jsonResponse: " + jsonResponse);
        ResponseCategory offlineCategory = APIResponse.getObjectFromJSONString(jsonResponse, ResponseCategory.class);
        if (offlineCategory != null) {
            Logger.d(TAG, "offlineCategory: " + offlineCategory.toString());
            categories.addAll(offlineCategory.getData());
        }
        return categories;
    }

    public static List<Product> getAllProducts(Context context, Subcategory subcategory) {
        List<Product> products = new ArrayList<>();
        String filePath = "";
        if (subcategory.getName().equalsIgnoreCase("Rice")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_rice.json";
        } else if (subcategory.getName().equalsIgnoreCase("Beverage")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_beverage.json";
        } else if (subcategory.getName().equalsIgnoreCase("Bakery")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_bakery.json";
        } else if (subcategory.getName().equalsIgnoreCase("Ice-Cream")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_ice_cream.json";
        } else if (subcategory.getName().equalsIgnoreCase("Chocolate")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_chocolate.json";
        } else if (subcategory.getName().equalsIgnoreCase("Cake")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_cake.json";
        } else if (subcategory.getName().equalsIgnoreCase("Ear ring")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_ear_ring.json";
        } else if (subcategory.getName().equalsIgnoreCase("Chicken")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_chicken.json";
        } else if (subcategory.getName().equalsIgnoreCase("Sweet")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_sweet.json";
        } else if (subcategory.getName().equalsIgnoreCase("Carrot")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_carrot.json";
        } else if (subcategory.getName().equalsIgnoreCase("Khata")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_khata.json";
        } else if (subcategory.getName().equalsIgnoreCase("Bulb")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_bulb.json";
        } else if (subcategory.getName().equalsIgnoreCase("Paracetamol")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_paracetamol.json";
        } else if (subcategory.getName().equalsIgnoreCase("Television")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_television.json";
        } else if (subcategory.getName().equalsIgnoreCase("Box")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_box.json";
        }
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, filePath);
        Logger.d(TAG, TAG + ">>getAllProducts>>jsonResponse: " + jsonResponse);
        ResponseProduct offlineProduct = APIResponse.getObjectFromJSONString(jsonResponse, ResponseProduct.class);
        if (offlineProduct != null) {
            Logger.d(TAG, "offlineProduct: " + offlineProduct.toString());
            products.addAll(offlineProduct.getData());
        }
        return products;
    }

    public static List<Product> getAllProducts(Context context, String name) {
        List<Product> products = new ArrayList<>();
        String filePath = "";
        if (name.equalsIgnoreCase("Rice")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_rice.json";
        } else if (name.equalsIgnoreCase("Beverage")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_beverage.json";
        } else if (name.equalsIgnoreCase("Bakery")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_bakery.json";
        } else if (name.equalsIgnoreCase("Ice-Cream")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_ice_cream.json";
        } else if (name.equalsIgnoreCase("Chocolate")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_chocolate.json";
        } else if (name.equalsIgnoreCase("Cake")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_cake.json";
        } else if (name.equalsIgnoreCase("Ear ring")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_ear_ring.json";
        } else if (name.equalsIgnoreCase("Chicken")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_chicken.json";
        } else if (name.equalsIgnoreCase("Sweet")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_sweet.json";
        } else if (name.equalsIgnoreCase("Carrot")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_carrot.json";
        } else if (name.equalsIgnoreCase("Khata")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_khata.json";
        } else if (name.equalsIgnoreCase("Bulb")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_bulb.json";
        } else if (name.equalsIgnoreCase("Paracetamol")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_paracetamol.json";
        } else if (name.equalsIgnoreCase("Television")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_television.json";
        } else if (name.equalsIgnoreCase("Box")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_box.json";
        } else if (name.equalsIgnoreCase("Popular")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_popular.json";
        } else if (name.equalsIgnoreCase("New")) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + "product_list_new.json";
        }
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, filePath);
        Logger.d(TAG, TAG + ">>getAllProducts>>jsonResponse: " + jsonResponse);
        ResponseProduct offlineProduct = APIResponse.getObjectFromJSONString(jsonResponse, ResponseProduct.class);
        if (offlineProduct != null) {
            Logger.d(TAG, "offlineProduct: " + offlineProduct.toString());
            products.addAll(offlineProduct.getData());
        }
        return products;
    }

    public static List<String> getUniqueVendorKeys(List<Product> products) {
        List<String> keys = new ArrayList<>();
        for (Product product : products) {
            if (!TextUtils.isEmpty(product.getVendor()) && !keys.contains(product.getVendor())) {
                keys.add(product.getVendor());
            }
        }
        Collections.sort(keys);
        return keys;
    }
}