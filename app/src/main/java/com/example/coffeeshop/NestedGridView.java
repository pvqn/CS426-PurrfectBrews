package com.example.coffeeshop;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class NestedGridView extends GridView {

    private boolean isHorizontalView = false;

    public NestedGridView(Context context) {
        super(context);
    }

    public NestedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIsHorizontalView(boolean isHorizontalView) {
        this.isHorizontalView = isHorizontalView;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isHorizontalView) {
            // Calculate the height of the GridView to fit all its children
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            // Measure the GridView normally for horizontal view
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (isHorizontalView) {
            // Make sure the GridView's height is properly calculated and does not exceed the screen height
            super.onLayout(changed, left, top, right, bottom);
            int height = getMeasuredHeight();
            int maxHeight = ((ViewGroup) getParent()).getMeasuredHeight();
            if (height > maxHeight) {
                getLayoutParams().height = maxHeight;
                setMeasuredDimension(getMeasuredWidth(), maxHeight);
            }
        } else {
            // Layout the GridView normally for horizontal view
            super.onLayout(changed, left, top, right, bottom);
        }
    }
}
