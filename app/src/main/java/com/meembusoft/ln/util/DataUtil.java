package com.meembusoft.ln.util;

import android.content.Context;

import com.meembusoft.ln.model.colormatchtab.Category;
import com.meembusoft.ln.model.colormatchtab.Product;
import com.meembusoft.ln.model.colormatchtab.Subcategory;
import com.meembusoft.ln.model.colormatchtab.response.ResponseCategory;
import com.meembusoft.ln.model.colormatchtab.response.ResponseProduct;
import com.meembusoft.retrofitmanager.APIResponse;

import java.util.ArrayList;
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
    public static final String ASSET_FILE_NAME_PRODUCT = "product_list_rice.json";
    public static final String ASSET_FILE_PATH_CATEGORY_WITH_SUBCATEGORY = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_CATEGORY_WITH_SUBCATEGORY;
    public static final String ASSET_FILE_PATH_PRODUCT = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_PRODUCT;

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
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, ASSET_FILE_PATH_PRODUCT);
        Logger.d(TAG, TAG + ">>getAllProducts>>jsonResponse: " + jsonResponse);
        ResponseProduct offlineProduct = APIResponse.getObjectFromJSONString(jsonResponse, ResponseProduct.class);
        if (offlineProduct != null) {
            Logger.d(TAG, "offlineProduct: " + offlineProduct.toString());
            products.addAll(offlineProduct.getData());
        }
        return products;
    }
}