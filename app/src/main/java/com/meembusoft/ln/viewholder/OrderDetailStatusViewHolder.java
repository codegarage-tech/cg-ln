package com.meembusoft.ln.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meembusoft.ln.R;
import com.meembusoft.ln.enumeration.OrderStatusType;
import com.meembusoft.ln.enumeration.OrderStepperStatus;
import com.meembusoft.ln.model.OrderStatus;
import com.meembusoft.recyclerview.viewholder.BaseViewHolder;
import com.reversecoder.timelineview.TimeLineView;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class OrderDetailStatusViewHolder extends BaseViewHolder<OrderStatus> {

    private TimeLineView timelineItem;
    private TextView tvOrderDetailStatusTimestamp, tvOrderDetailStatusName;
    private LinearLayout llCompleted;
    private ImageView ivStepperStatus;

    public OrderDetailStatusViewHolder(ViewGroup parent) {
        super(parent, R.layout.row_item_order_detail_status);

        timelineItem = (TimeLineView) $(R.id.timeline_order_detail_status);
        tvOrderDetailStatusTimestamp = (TextView) $(R.id.tv_order_detail_status_timestamp);
        tvOrderDetailStatusName = (TextView) $(R.id.tv_order_detail_status_name);
        llCompleted = (LinearLayout) $(R.id.ll_completed);
        ivStepperStatus = (ImageView) $(R.id.iv_stepper_status);
    }

    @Override
    public void setData(final OrderStatus data) {
        //Define timeline item position
        int dataCount = getOwnerAdapter().getItemCount();
        int dataPosition = getDataPosition();
        if (dataCount == 1) {
            timelineItem.setPositionType(TimeLineView.POSITION_TYPE_PLAIN);
        } else if (dataCount == 2) {
            if (dataPosition == 0) {
                timelineItem.setPositionType(TimeLineView.POSITION_TYPE_FIRST);
            } else if (dataPosition == 1) {
                timelineItem.setPositionType(TimeLineView.POSITION_TYPE_LAST);
            }
        } else if (dataCount > 2) {
            if (dataPosition == 0) {
                timelineItem.setPositionType(TimeLineView.POSITION_TYPE_FIRST);
            } else if (dataPosition == (dataCount - 1)) {
                timelineItem.setPositionType(TimeLineView.POSITION_TYPE_LAST);
            } else {
                timelineItem.setPositionType(TimeLineView.POSITION_TYPE_MIDDLE);
            }
        }

        //Define status finish state
        OrderStepperStatus orderStepperStatus = OrderStepperStatus.getOrderStepperStatus(data.getIsFinished());
        switch (orderStepperStatus) {
            case COMPLETED:
                if (data.getStatus() == OrderStatusType.ORDER_CANCELED.getOrderStatusType()) {
                    ivStepperStatus.setBackgroundResource(R.drawable.vector_canceled);
                } else {
                    ivStepperStatus.setBackgroundResource(R.drawable.vector_accepted);
                }
                tvOrderDetailStatusName.setTextColor(getContext().getResources().getColor(R.color.white));
                break;
            case INACTIVE:
                if (data.getStatus() == OrderStatusType.ORDER_CANCELED.getOrderStatusType()) {
                    ivStepperStatus.setBackgroundResource(R.drawable.vector_canceled_grey);
                } else {
                    ivStepperStatus.setBackgroundResource(R.drawable.vector_accepted_grey);
                }
                tvOrderDetailStatusName.setTextColor(getContext().getResources().getColor(R.color.white80));
                break;
        }

        //Define timeline item type
        if (dataPosition % 2 == 0) {
//            ViewGroup.LayoutParams param = timelineItem.getLayoutParams();
//            param.height = 200;
//            timelineItem.setLayoutParams(param);
            timelineItem.setBackgroundResource(R.drawable.drop_shadow_rectangle_round_lefttop_round_rightbottom);
            timelineItem.setZoneType(TimeLineView.ZONE_TYPE_ITEM);
        } else {
            ViewGroup.LayoutParams param = timelineItem.getLayoutParams();
            param.height = 30;
            timelineItem.setLayoutParams(param);
//            timelineItem.setRadioBackgroundColor(Color.parseColor("#424242"));

            timelineItem.setBackgroundResource(R.drawable.button_inverse_selector);
            timelineItem.setZoneType(TimeLineView.ZONE_TYPE_LINE);
            llCompleted.setVisibility(View.GONE);
        }

        tvOrderDetailStatusTimestamp.setText(data.getIsFinished() == 1 ? data.getTimestamp() : "---");
        tvOrderDetailStatusName.setText(OrderStatusType.getOrderStatusTypeDescription(data.getStatus()));
    }
}