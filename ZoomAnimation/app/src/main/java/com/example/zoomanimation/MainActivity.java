package com.example.zoomanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Animator mCurrentAnimator;
    private ImageView mImageViewExpanded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton imageViewThumbnail = findViewById(R.id.imageViewThumbnail);
        imageViewThumbnail.setImageBitmap(
                loadSampleResource(R.drawable.jellyfish,
                100, 100));

        imageViewThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomFromThumbnail(imageViewThumbnail);
            }
        });

        mImageViewExpanded = findViewById(R.id.imageViewExpanded);
        mImageViewExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageViewExpanded.setVisibility(View.GONE);
                mImageViewExpanded.setImageBitmap(null);
                imageViewThumbnail.setVisibility(View.VISIBLE);
            }
        });
    }

    private void zoomFromThumbnail(final ImageButton imageViewThumb) {
        if(mCurrentAnimator != null){
            mCurrentAnimator.cancel();
        }

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        imageViewThumb.getGlobalVisibleRect(startBounds);
        findViewById(R.id.frameLayout).getGlobalVisibleRect(finalBounds, globalOffset);

        mImageViewExpanded.setImageBitmap(
                loadSampleResource(R.drawable.jellyfish,
                        finalBounds.height(),
                        finalBounds.width())
        );

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if((float)finalBounds.width() / finalBounds.height() >
                (float) startBounds.width() / startBounds.height()){
            startScale = (float) startBounds.height() / finalBounds.height();

            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width() / 2);
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        }else{
            startScale = (float) startBounds.width() / finalBounds.width();

            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height() / 2);
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        imageViewThumb.setVisibility(View.GONE);
        mImageViewExpanded.setVisibility(View.VISIBLE);
        mImageViewExpanded.setPivotX(0f);
        mImageViewExpanded.setPivotY(0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(
                ObjectAnimator.ofFloat(mImageViewExpanded, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(mImageViewExpanded, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(mImageViewExpanded, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(mImageViewExpanded, View.SCALE_Y, startScale, 1f));
        animatorSet.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animatorSet.start();
        mCurrentAnimator = animatorSet;
    }

    public Bitmap loadSampleResource(int imageID, int targetHeight, int targetWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageID, options);
        final int originalHeight = options.outHeight;
        final int originalWidth = options.outWidth;
        int inSampleSize = 1;
        while ((originalHeight / (inSampleSize * 2)) > targetHeight &&
                (originalWidth / (inSampleSize * 2)) > targetWidth) {
            inSampleSize *= 2;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(getResources(), imageID, options);
    }
}