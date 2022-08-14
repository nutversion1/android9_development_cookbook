package com.example.circleimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.PostProcessor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    PostProcessor mCirclePostProcessor = new PostProcessor() {
        @Override
        public int onPostProcess(@NonNull Canvas canvas) {
            Path path = new Path();
            path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            path.addCircle(width/2,height/2,400, Path.Direction.CW);
            //path.addRoundRect(0,0,width, height, 250,250, Path.Direction.CW);

            Paint paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            paint.setAntiAlias(true);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

            canvas.drawPath(path, paint);

            return PixelFormat.TRANSLUCENT;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadImage();
    }

    private void loadImage() {
        ImageDecoder.Source source = ImageDecoder.createSource(
                getResources(),
                R.drawable.eclipse);

        ImageDecoder.OnHeaderDecodedListener listener = new ImageDecoder.OnHeaderDecodedListener() {
            @Override
            public void onHeaderDecoded(@NonNull ImageDecoder imageDecoder, @NonNull ImageDecoder.ImageInfo imageInfo, @NonNull ImageDecoder.Source source) {
                imageDecoder.setPostProcessor(mCirclePostProcessor);
            }
        };

        try {
            Drawable drawable = ImageDecoder.decodeDrawable(source, listener);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}