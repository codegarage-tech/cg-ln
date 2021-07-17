package com.meembusoft.ln.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meembusoft.addtocart.AddToCartManager;
import com.meembusoft.addtocart.model.CartItem;
import com.meembusoft.ln.R;
import com.meembusoft.ln.adapter.CartListAdapter;
import com.meembusoft.ln.base.BaseActivity;
import com.meembusoft.ln.util.Constants;
import com.meembusoft.ln.util.CookieBarUtil;
import com.meembusoft.ln.util.DataUtil;
import com.meembusoft.ln.util.ValidationManager;
import com.meembusoft.recyclerview.MRecyclerView;

import org.aviran.cookiebar2.OnActionClickListener;

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
    private AppCompatButton btnOrderNow;
    private boolean isAbortAllSelection = false, isMultiSelection = false;
    private int subTotal = 0;
    private EditText edtDeliveryName, edtDeliveryMobileNumber, edtDeliveryAddress;
    private AppCompatCheckBox accbCashOnDelivery;

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
        edtDeliveryMobileNumber = findViewById(R.id.edt_delivery_mobile_no);
        edtDeliveryAddress = findViewById(R.id.edt_delivery_address);
        edtDeliveryName = findViewById(R.id.edt_delivery_name);
        accbCashOnDelivery = findViewById(R.id.cb_cash_on_delivery);
    }

    @Override
    public void initViewsData(Bundle savedInstanceState) {
        // Toolbar
        tvTitle.setText(R.string.txt_cart);
        updateAllCartItemsSelection(AddToCartManager.getInstance().isAllCartItemsSelected(), false);
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
                if (!isAbortAllSelection()) {
                    setMultiSelection(true);
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
                    rvCart.post(new Runnable() {
                        @Override
                        public void run() {
                            setMultiSelection(false);
                        }
                    });
                } else {
                    setAbortAllSelection(false);
                }
            }
        });

        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate oder items
                if (AddToCartManager.getInstance().getSelectedCartItemCount() == 0) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_select_at_least_one_product));
                    return;
                }
                // Validate total
                if (subTotal < (Constants.MINIMUM_ORDER_AMOUNT)) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_minimum_order_amount_excluding_delivery_charge));
                    return;
                }
                // Validate delivery information
                if (TextUtils.isEmpty(edtDeliveryName.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_delivery_name));
                    return;
                }
                if (TextUtils.isEmpty(edtDeliveryMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_delivery_mobile_number));
                    return;
                }
                if (!ValidationManager.isValidBangladeshiMobileNumber(edtDeliveryMobileNumber.getText().toString())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_valid_delivery_mobile_number));
                    return;
                }
                if (TextUtils.isEmpty(edtDeliveryAddress.getText())) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_input_a_delivery_address));
                    return;
                }
                if (!accbCashOnDelivery.isChecked()) {
                    CookieBarUtil.showCookieBarWarning(getActivity(), getString(R.string.txt_please_select_the_payment_system));
                    return;
                }
                // Remove all cart items after success order
                AddToCartManager.getInstance().deleteAllCartItems(CartItem.class);

                // Check confirmation for saving address
                CookieBarUtil.showCookieBarConfirmation(getActivity(), getString(R.string.txt_do_you_want_to_save_the_address), new OnActionClickListener() {
                    @Override
                    public void onClick() {

                    }
                });
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

    @Override
    public void onAllPermissionsAccepted() {

    }

    public void updateAllCartItemsSelection(boolean isSelected, boolean isAbortAllSelection) {
        setAbortAllSelection(isAbortAllSelection);
        accbSelectAll.setChecked(isSelected);
    }

    public void updateSummery() {
        subTotal = AddToCartManager.getInstance().getSubTotalPrice();
        int deliveryCharge = ((subTotal == 0) ? 0 : Constants.MINIMUM_DELIVERY_CHARGE);
        int grandTotal = subTotal + deliveryCharge;
        int selectedItemCount = AddToCartManager.getInstance().getSelectedCartItemCount();
        tvSubtotal.setText(getString(R.string.txt_amount_with_taka, subTotal));
        tvDeliveryCharge.setText(getString(R.string.txt_amount_with_taka, deliveryCharge));
        tvGrandTotal.setText(getString(R.string.txt_amount_with_taka, grandTotal));
        tvTotal.setText(getString(R.string.txt_amount_with_taka, grandTotal));
        btnOrderNow.setText(getString(R.string.txt_order_now, selectedItemCount));
    }

    public boolean isAbortAllSelection() {
        return isAbortAllSelection;
    }

    public void setAbortAllSelection(boolean abortAllSelection) {
        this.isAbortAllSelection = abortAllSelection;
    }

    public boolean isMultiSelection() {
        return isMultiSelection;
    }

    public void setMultiSelection(boolean multiSelection) {
        isMultiSelection = multiSelection;
    }
}