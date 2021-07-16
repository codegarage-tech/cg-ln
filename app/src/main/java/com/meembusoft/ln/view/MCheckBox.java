package com.meembusoft.ln.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

// https://newbedev.com/change-checkbox-value-without-triggering-oncheckchanged
// https://stackoverflow.com/questions/15523157/change-checkbox-value-without-triggering-oncheckchanged
// https://gist.github.com/curioustechizen/81d793f4b14bb3f649d1

public class MCheckBox extends AppCompatCheckBox {

    private OnCheckedChangeListener mListener;

    public MCheckBox(final Context context) {
        super(context);
    }

    public MCheckBox(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MCheckBox(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        mListener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    public void setChecked(final boolean checked, final boolean alsoNotify) {
        if (!alsoNotify) {
            super.setOnCheckedChangeListener(null);
            super.setChecked(checked);
            super.setOnCheckedChangeListener(mListener);
            return;
        }
        super.setChecked(checked);
    }

    public void toggle(boolean alsoNotify) {
        if (!alsoNotify) {
            super.setOnCheckedChangeListener(null);
            super.toggle();
            super.setOnCheckedChangeListener(mListener);
            return;
        }
        super.toggle();
    }
}