package com.poliveira.apps.imagewindow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
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

    private final int CLOSE_SIZE = 32; //google messenger approx 27
    private ImageView mImageView;

    public ImageWindow(Context context) {
        super(context);
        init();
    }

    public ImageWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;
        ImageView closeButton = new ImageView(getContext());
        closeButton.setLayoutParams(new RelativeLayout.LayoutParams((int) (CLOSE_SIZE * density), (int) (CLOSE_SIZE * density)));
        closeButton.setBackgroundResource(R.drawable.close_button_drawable);
        closeButton.setImageResource(R.drawable.ic_action_close);
        mImageView = new CustomImageView(getContext(), CLOSE_SIZE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) (10 * density);
        params.setMargins(margin, margin, 0, 0);
        mImageView.setLayoutParams(params);
        mImageView.setAdjustViewBounds(true);
        addView(mImageView);
        addView(closeButton);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setOnCloseListener(final OnCloseListener onCloseListener) {
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseListener.onCloseClick(ImageWindow.this);
            }
        });
    }
    //***********************************************

    static class CustomImageView extends ImageView {

        private float mCloseSize;
        private Paint mEraser;
        private RectF mRectangle;
        private Path mRectanglePath;
        private int RECTANGLE_CORNER = 7;
        private float mDensity;

        public CustomImageView(Context context, float closeSize) {
            super(context);
            mCloseSize = closeSize;
            init();
        }

        private void init() {
            mEraser = new Paint();
            mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mEraser.setAntiAlias(true);
            mRectanglePath = new Path();
            mDensity = getResources().getDisplayMetrics().density;
            RECTANGLE_CORNER = (int) (RECTANGLE_CORNER * mDensity);
        }


        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (w != oldw || h != oldh) {
                mRectanglePath.reset();
                mRectangle = new RectF(0, 0, getWidth(), getHeight());
                mRectanglePath.addRoundRect(mRectangle, RECTANGLE_CORNER, RECTANGLE_CORNER, Path.Direction.CW);
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
            canvas.drawCircle((int) ((mCloseSize * 0.5) * mDensity - ((LayoutParams) getLayoutParams()).leftMargin),
                    (int) ((mCloseSize * 0.5) * mDensity - ((LayoutParams) getLayoutParams()).topMargin),
                    (int) (((mCloseSize * 0.5) + 3) * mDensity), mEraser);
        }
    }
}
