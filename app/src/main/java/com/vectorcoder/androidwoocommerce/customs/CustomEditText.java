package com.vectorcoder.androidwoocommerce.customs;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by Muhammad Nabeel on 31/10/2018.
 */
public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public CustomEditText(Context context) {
        super(context);
    }
    
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        this.setSelection(this.getText().length());
    }
}
