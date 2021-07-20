package com.meembusoft.ln.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.interfaces.OnCartResetListener;
import com.meembusoft.ln.model.Category;
import com.meembusoft.ln.model.Order;
import com.meembusoft.ln.model.OrderDetail;
import com.meembusoft.ln.model.Product;
import com.meembusoft.ln.model.Subcategory;
import com.meembusoft.ln.model.Suggestion;
import com.meembusoft.ln.model.Unit;
import com.meembusoft.ln.model.response.ResponseCategory;
import com.meembusoft.ln.model.response.ResponseOrder;
import com.meembusoft.ln.model.response.ResponseOrderDetail;
import com.meembusoft.ln.model.response.ResponseProduct;
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
    public static final String ASSET_FILE_NAME_ORDERS = "order_list.json";
    public static final String ASSET_FILE_NAME_ORDER_DETAIL_ACCEPTED = "order_detail_accepted.json";
    public static final String ASSET_FILE_NAME_ORDER_DETAIL_CANCELED = "order_detail_canceled.json";
    public static final String ASSET_FILE_NAME_ORDER_DETAIL_COMPLETED = "order_detail_completed.json";
    public static final String ASSET_FILE_PATH_CATEGORY_WITH_SUBCATEGORY = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_CATEGORY_WITH_SUBCATEGORY;
    public static final String ASSET_FILE_PATH_ORDERS = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_ORDERS;

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

    public static List<Order> getAllOrders(Context context) {
        List<Order> orders = new ArrayList<>();
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, ASSET_FILE_PATH_ORDERS);
        Logger.d(TAG, TAG + ">>getAllOrders>>jsonResponse: " + jsonResponse);
        ResponseOrder offlineOrders = APIResponse.getObjectFromJSONString(jsonResponse, ResponseOrder.class);
        if (offlineOrders != null) {
            Logger.d(TAG, "offlineOrders: " + offlineOrders.toString());
            orders.addAll(offlineOrders.getData());
        }
        return orders;
    }

    public static OrderDetail getOrderDetail(Context context, int currentStatus) {
        String filePath = "";
        if (currentStatus == 8) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_ORDER_DETAIL_CANCELED;
        } else if (currentStatus == 7) {
            filePath = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_ORDER_DETAIL_COMPLETED;
        } else {
            filePath = ASSET_API_RESPONSE_BASE_PATH + ASSET_FILE_NAME_ORDER_DETAIL_ACCEPTED;
        }
        String jsonResponse = AndroidAssetManager.readTextFileFromAsset(context, filePath);
        Logger.d(TAG, TAG + ">>getOrderDetail>>jsonResponse: " + jsonResponse);
        ResponseOrderDetail offlineOrderDetail = APIResponse.getObjectFromJSONString(jsonResponse, ResponseOrderDetail.class);
        if (offlineOrderDetail != null) {
            Logger.d(TAG, "offlineOrderDetail: " + offlineOrderDetail.toString());
            return offlineOrderDetail.getData();
        }

        return null;
    }

    public static List<Suggestion> getAllSuggestions(Context context) {
        List<Suggestion> suggestions = new ArrayList<>();

        // Category
        List<Category> categories = new ArrayList<>();
        categories.addAll(getAllCategoriesWithSubcategories(context));

        // Subcategory
        List<Subcategory> subcategories = new ArrayList<>();
        for (Category category : categories) {
            subcategories.addAll(category.getSubcategory());
        }

        // Suggestion
        suggestions.addAll(subcategories);
        suggestions.addAll(categories);

        Collections.sort(subcategories);

        return suggestions;
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

    public static Unit getUnit(String unitName, List<Unit> units) {
        if (!TextUtils.isEmpty(unitName) && units != null && !units.isEmpty()) {
            for (Unit mUnit : units) {
                if (mUnit.getName().equalsIgnoreCase(unitName)) {
                    return mUnit;
                }
            }
        }
        return null;
    }

    public static Product getProduct(List<Product> products, String productId) {
        if (products != null && !products.isEmpty() && !TextUtils.isEmpty(productId)) {
            for (Product product : products) {
                String pId = product.getId() + "";
                if (pId.contains(productId)) {
                    return product;
                }
            }
        }
        return null;
    }

    public static List<Product> setUnitWiseQuantity(List<Product> products, List<CartItem> cartItems) {
        if (products != null && !products.isEmpty() && cartItems != null && !cartItems.isEmpty()) {
            for (CartItem cartItem : cartItems) {
                Product selectedProduct = getProduct(products, cartItem.getId().split(">>")[0]);
                if (selectedProduct != null) {
                    selectedProduct.setSelectedQuantity(cartItem.getSize(), cartItem.getQuantity());
                }
            }
        }
        return products;
    }

    public static String getCartId(Product product, Unit unit) {
        return product.getId() + ">>" + unit.getName();
    }

    public static void resetCartCounterView(TextView counterView, OnCartResetListener onCartResetListener) {
        List<CartItem> data = AddToCartManager.getInstance().getAllCartItems(CartItem.class);

        if (data != null && data.size() > 0) {
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "count: " + data.size());
            Log.d(TAG, "<<<onOrderNowClick>>>: " + "data: " + data.toString());
            counterView.setText(data.size() + "");
            counterView.setVisibility(View.VISIBLE);
            if (onCartResetListener != null) {
                onCartResetListener.onOrderCompleted(false);
            }
        } else {
            counterView.setVisibility(View.GONE);
            if (onCartResetListener != null) {
                onCartResetListener.onOrderCompleted(true);
            }
        }
    }

    public static List<CartItem> getAllCartItems() {
        return AddToCartManager.getInstance().getAllCartItems(CartItem.class);
    }

    public static CartItem prepareCartItem(Product data, Unit selectedUnit, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setId(DataUtil.getCartId(data, selectedUnit));
        cartItem.setName(data.getName());
        cartItem.setSize(selectedUnit.getName());
        cartItem.setPrice(((double) selectedUnit.getOriginalPrice()));
        cartItem.setQuantity(quantity);
        cartItem.setImage(data.getImages().get(0).getUrl());
        return cartItem;
    }
}