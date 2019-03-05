package com.lzf.productionlinecontrol.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by MJCoder on 2019-03-01.
 */

@SuppressLint("AppCompatCustomView")
public class CursorListenerEditText extends EditText {

    private OnSelectionChangedListener onSelectionChangedListener;

    public void setOnSelectionChangedListener(OnSelectionChangedListener onSelectionChangedListener) {
        this.onSelectionChangedListener = onSelectionChangedListener;
    }

    public interface OnSelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

    public CursorListenerEditText(Context context) {
        super(context);
    }

    public CursorListenerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CursorListenerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CursorListenerEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //        Log.v("onSelectionChanged ", "selStart " + selStart + " selEnd " + selEnd);
        if (onSelectionChangedListener != null) {
            onSelectionChangedListener.onSelectionChanged(selStart, selEnd);
        }
    }
}
