package com.meembusoft.ln.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.model.OrderItem;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class OrderDetailItemViewHolder extends BaseViewHolder<OrderItem> {

    private ImageView ivProductImage;
    private TextView tvProductName, tvProductUnitName, tvProductUnitPrice, tvProductQuantity, tvProductSumPrice;
    private String TAG = "OrderDetailItemViewHolder";

    public OrderDetailItemViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_order_item);

        ivProductImage = $(R.id.iv_product_image);
        tvProductName = $(R.id.tv_product_name);
        tvProductUnitName = $(R.id.tv_product_unit_name);
        tvProductUnitPrice = $(R.id.tv_product_unit_price);
        tvProductQuantity = $(R.id.tv_product_quantity);
        tvProductSumPrice = $(R.id.tv_product_sum_price);
    }

    @Override
    public void setData(final OrderItem data) {
        Picasso.get().load(data.getProduct_image()).into(ivProductImage);
        tvProductName.setText(data.getProduct_name());
        tvProductUnitName.setText(data.getProduct_unit());
        tvProductUnitPrice.setText(getContext().getString(R.string.txt_amount_with_taka, data.getProduct_unit_price()));
        tvProductQuantity.setText(data.getQuantity() + "");
        tvProductSumPrice.setText(getContext().getString(R.string.txt_amount_with_taka, (data.getQuantity() * data.getProduct_unit_price())));
    }
}