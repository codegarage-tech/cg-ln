package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CartListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.recyclerview.MRecyclerView;

import java.util.List;

public class CartActivity extends BaseActivity {

    // Toolbar
    private TextView tvTitle;
    private LinearLayout llClose;
    private AppCompatCheckBox accbSelectAll;

    // View items
    private MRecyclerView rvCart;
    private CartListAdapter mCartListAdapter;
    private TextView tvSubtotal, tvDeliveryCharge, tvGrandTotal, tvTotal;
    private Button btnOrderNow;
    private boolean isSingleSelection = false;

    @Override
    public int initToolbarLayout() {
        return R.layout.toolbar_cart;
    }

    @Override
    public int initScreenLayout() {
        return R.layout.activity_cart;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initViews() {
        // Toolbar
        tvTitle = findViewById(R.id.tv_title);
        llClose = findViewById(R.id.ll_close);
        accbSelectAll = findViewById(R.id.cb_select_all);

        // View items
        rvCart = findViewById(R.id.rv_cart);
        tvSubtotal = findViewById(R.id.tv_product_subtotal);
        tvDeliveryCharge = findViewById(R.id.tv_product_delivery_charge);
        tvGrandTotal = findViewById(R.id.tv_product_grand_total);
        tvTotal = findViewById(R.id.tv_total_price);
        btnOrderNow = findViewById(R.id.btn_order_now);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_cart);
        updateAllCartItemsSelection(AddToCartManager.getInstance().isAllCartItemsSelected());
        // Update summery view
        updateSummery();

        // Setup cart recyclerview
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCartListAdapter = new CartListAdapter(getActivity());
        // Load cart adapter
        rvCart.setAdapter(mCartListAdapter);
        // Load cart items into adapter
        mCartListAdapter.addAll(DataUtil.getAllCartItems());
    }

    @Override
    public void initActions(Bundle savedInstanceState) {
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBackPress();
            }
        });

        accbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCartListAdapter != null) {

                    List<CartItem> cartItems = mCartListAdapter.getAllData();
                    for (CartItem cartItem : cartItems) {
                        cartItem.setSelected(isChecked);
                        AddToCartManager.getInstance().addOrUpdateCart(cartItem);
                    }
                    mCartListAdapter.notifyDataSetChanged();
                }

                // Calculate total
                updateSummery();
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initBackPress() {
        finish();
    }

    @Override
    public void initDestroyTasks() {

    }

    public void updateAllCartItemsSelection(boolean isSelected) {
        accbSelectAll.setChecked(isSelected);
    }

    public void updateSummery() {
        int subTotal = AddToCartManager.getInstance().getSubTotalPrice();
        int deliveryCharge = ((subTotal == 0) ? 0 : 10);
        int grandTotal = subTotal + deliveryCharge;
        int selectedItemCount = AddToCartManager.getInstance().getSelectedCartItemCount();
        tvSubtotal.setText(subTotal + "Tk");
        tvDeliveryCharge.setText(deliveryCharge + " Tk");
        tvGrandTotal.setText(grandTotal + " Tk");
        tvTotal.setText(grandTotal + " Tk");
        btnOrderNow.setText(getString(R.string.txt_order_now, selectedItemCount));
    }

    public boolean isSingleSelection() {
        return isSingleSelection;
    }

    public void setSingleSelection(boolean singleSelection) {
        isSingleSelection = singleSelection;
    }
}