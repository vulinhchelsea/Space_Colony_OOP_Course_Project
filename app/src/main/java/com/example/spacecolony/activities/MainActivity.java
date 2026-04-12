package com.example.spacecolony.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolony.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize clicking area
        setupArea(R.id.img_medbay, MedBayActivity.class);
        setupArea(R.id.img_training, TrainingActivity.class);
        setupArea(R.id.img_missioncontrol, MissionActivity.class);
        setupArea(R.id.img_team, TeamActivity.class);
        setupArea(R.id.img_record, RecordActivity.class);
        setupArea(R.id.img_recruitment, RecruitmentActivity.class);
    }

    // accessibility function
    @SuppressLint("ClickableViewAccessibility")
    // setupArea function for all image
    private void setupArea(int viewId, Class<?> targetActivity) {
        ImageView imageView = findViewById(viewId);
        if (imageView == null) return;

        imageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (isTouchOnSprite(v, event)) {
                    v.performClick();
                    // start target activity, screen transition
                    Intent intent = new Intent(MainActivity.this, targetActivity);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        });
    }

    // function for on touch filter
    private boolean isTouchOnSprite(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ImageView imageView = (ImageView) v;
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) return false;

            Bitmap bitmap;

            // drawable checking
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                android.graphics.Canvas canvas = new android.graphics.Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }

            // pixel color checking
            float x = event.getX() * bitmap.getWidth() / v.getWidth();
            float y = event.getY() * bitmap.getHeight() / v.getHeight();

            if (x < 0 || x >= bitmap.getWidth() || y < 0 || y >= bitmap.getHeight()) {
                return false;
            }

            int pixel = bitmap.getPixel((int) x, (int) y);
            return Color.alpha(pixel) > 0;
        }
        return false;
    }
}