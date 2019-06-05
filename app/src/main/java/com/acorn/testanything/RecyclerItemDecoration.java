package com.acorn.testanything;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Acorn on 2016/9/13.
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    private int firstItemTopDecoration = 0;
    private int firstItemLeftDecoration = 0;
    private int lastItemRightDecoration = 0;

    /**
     * @param context
     * @param orientation
     * @param intervalDp  间隔距离(dp)
     */
    public RecyclerItemDecoration(Context context, int orientation, int intervalDp) {
        final int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                intervalDp, context.getResources().getDisplayMetrics());
        mDivider = new ColorDrawable(0xff00a690) {

            @Override
            public int getIntrinsicHeight() {
                return size;
            }

            @Override
            public int getIntrinsicWidth() {
                return size;
            }
        };
        setOrientation(orientation);
    }

    public RecyclerItemDecoration(Context context, int orientation, Drawable mDivider) {
        this.mDivider = mDivider;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastRawOrCol(int pos, int childCount) {
        return pos + 1 == childCount;
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if (mOrientation == VERTICAL_LIST) {
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//        } else {
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//        }
//    }


    public void setFirstItemLeftDecoration(int firstItemLeftDecoration) {
        this.firstItemLeftDecoration = firstItemLeftDecoration;
    }

    public void setFirstItemTopDecoration(int firstItemTopDecoration) {
        this.firstItemTopDecoration = firstItemTopDecoration;
    }

    public void setLastItemRightDecoration(int lastItemRightDecoration) {
        this.lastItemRightDecoration = lastItemRightDecoration;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
//        super.getItemOffsets(outRect, itemPosition, parent);
        int childCount = parent.getAdapter().getItemCount();
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(
                    itemPosition == 0 ? firstItemLeftDecoration : 0,
                    itemPosition == 0 ? firstItemTopDecoration : 0,
                    0, isLastRawOrCol(itemPosition, childCount) ? 0 :
                            100);
        } else {
            outRect.set(
                    itemPosition == 0 ? firstItemLeftDecoration : 0,
                    itemPosition == 0 ? firstItemTopDecoration : 0,
                    isLastRawOrCol(itemPosition, childCount) ? lastItemRightDecoration :
                            mDivider.getIntrinsicWidth(),
                    0);
        }
    }
}
