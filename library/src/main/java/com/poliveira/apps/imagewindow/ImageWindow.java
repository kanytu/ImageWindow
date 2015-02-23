package com.poliveira.apps.imagewindow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by poliveira on 18/02/2015.
 */
public class ImageWindow extends RelativeLayout {
    public interface OnCloseListener {
        void onCloseClick(View imageWindow);
    }

    private float mCloseButtonSize = 32; //google messenger approx 27
    private float mCloseButtonMargin = 5; //google messenger approx 27
    private int mCloseColor = 0xffff7b57; //google messenger approx 27
    private float mCornerRadius = 7; //google messenger approx 27
    private int mCloseIcon = R.drawable.ic_action_close;
    private ImageView mImageView;
    private float mTopLeftMargin = 10f;


    public ImageWindow(Context context) {
        super(context);
        init();
    }

    public ImageWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeParameters(attrs);
        init();
    }

    public ImageWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeParameters(attrs);
        init();
    }

    private void initializeParameters(AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.ImageWindow);
        float density = getResources().getDisplayMetrics().density;
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ImageWindow_close_color) {
                mCloseColor = a.getColor(R.styleable.ImageWindow_close_color, 0xffff7b57); //orange
            } else if (attr == R.styleable.ImageWindow_close_icon) {
                mCloseIcon = a.getResourceId(R.styleable.ImageWindow_close_icon, R.drawable.ic_action_close);
            } else if (attr == R.styleable.ImageWindow_close_margin) {
                mCloseButtonMargin = a.getDimension(R.styleable.ImageWindow_close_margin, 5f * density);
            } else if (attr == R.styleable.ImageWindow_close_size) {
                mCloseButtonSize = a.getDimension(R.styleable.ImageWindow_close_size, 32f * density);
            } else if (attr == R.styleable.ImageWindow_corner_radius) {
                mCornerRadius = a.getDimension(R.styleable.ImageWindow_corner_radius, 7f * density);
            } else if (attr == R.styleable.ImageWindow_image_margin) {
                mTopLeftMargin = a.getDimension(R.styleable.ImageWindow_image_margin, 10f * density);
            }
        }
        a.recycle();
    }

    private void init() {

        ImageView closeButton = new ImageView(getContext());
        closeButton.setLayoutParams(new RelativeLayout.LayoutParams((int) (mCloseButtonSize), (int) (mCloseButtonSize)));
        StateListDrawable drawable = new StateListDrawable();
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        ShapeDrawable shapePressed = new ShapeDrawable(new OvalShape());
        shape.setColorFilter(mCloseColor, PorterDuff.Mode.SRC_ATOP);
        shapePressed.setColorFilter(mCloseColor - 0x444444, PorterDuff.Mode.SRC_ATOP);//a little bit darker
        drawable.addState(new int[]{android.R.attr.state_pressed}, shapePressed);
        drawable.addState(new int[]{}, shape);
        closeButton.setImageResource(mCloseIcon);
        closeButton.setBackground(drawable); //todo change this to support lower api
        closeButton.setClickable(true);
        closeButton.setId(R.id.closeId);
        mImageView = new CustomImageView(getContext(), mCloseButtonSize, mCloseButtonMargin, mCornerRadius);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Math.round(mTopLeftMargin), Math.round(mTopLeftMargin), 0, 0);
        mImageView.setLayoutParams(params);
        mImageView.setAdjustViewBounds(true);
        addView(mImageView);
        addView(closeButton);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public float getCloseButtonSize() {
        return mCloseButtonSize;
    }

    public void setCloseButtonSize(float closeButtonSize) {
        mCloseButtonSize = closeButtonSize;
    }

    public float getCloseButtonMargin() {
        return mCloseButtonMargin;
    }

    public void setCloseButtonMargin(float closeButtonMargin) {
        mCloseButtonMargin = closeButtonMargin;
    }

    public int getCloseColor() {
        return mCloseColor;
    }

    public void setCloseColor(int closeColor) {
        mCloseColor = closeColor;
    }

    public float getCornerRadius() {
        return mCornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
    }

    public int getCloseIcon() {
        return mCloseIcon;
    }

    public void setCloseIcon(int closeIcon) {
        mCloseIcon = closeIcon;
    }

    public float getTopLeftMargin() {
        return mTopLeftMargin;
    }

    public void setTopLeftMargin(float topLeftMargin) {
        mTopLeftMargin = topLeftMargin;
    }

    public void setOnCloseListener(final OnCloseListener onCloseListener) {
        findViewById(R.id.closeId).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseListener.onCloseClick(ImageWindow.this);
            }
        });
    }
    //***********************************************

    static class CustomImageView extends ImageView {

        private final float mCloseButtonMargin;
        private final float mCornerRadius;
        private float mCloseSize;
        private Paint mEraser;
        private RectF mRectangle;
        private Path mRectanglePath;

        public CustomImageView(Context context, float closeSize, float closeButtonMargin, float cornerRadius) {
            super(context);
            mCloseSize = closeSize;
            mCloseButtonMargin = closeButtonMargin;
            mCornerRadius = cornerRadius;
            init();
        }

        private void init() {
            mEraser = new Paint();
            mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mEraser.setAntiAlias(true);
            mRectanglePath = new Path();
        }


        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (w != oldw || h != oldh) {
                mRectanglePath.reset();
                mRectangle = new RectF(0, 0, getWidth(), getHeight());
                mRectanglePath.addRoundRect(mRectangle, mCornerRadius, mCornerRadius, Path.Direction.CW);
                mRectanglePath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            }
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.saveLayerAlpha(0, 0, canvas.getWidth(), canvas.getHeight(), 255, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
            super.onDraw(canvas);
            canvas.drawPath(mRectanglePath, mEraser);
            //5 is the margin of the close circle
            canvas.drawCircle((int) ((mCloseSize * 0.5) - ((LayoutParams) getLayoutParams()).leftMargin),
                    (int) ((mCloseSize * 0.5) - ((LayoutParams) getLayoutParams()).topMargin),
                    (int) (((mCloseSize * 0.5) + mCloseButtonMargin)), mEraser);
        }
    }
}
