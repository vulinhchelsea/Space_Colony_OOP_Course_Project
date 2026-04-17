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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup areas
        setupArea(R.id.img_medbay, MedBayActivity.class);
        setupArea(R.id.img_training, TrainingActivity.class);
        setupArea(R.id.img_missioncontrol, MissionActivity.class);
        setupArea(R.id.img_team, TeamActivity.class);
        setupArea(R.id.img_record, RecordActivity.class);
        setupArea(R.id.img_recruitment, RecruitmentActivity.class);
        
        findViewById(R.id.btn_help).setOnClickListener(v -> showHelp());
    }

    private void showHelp() {
        new AlertDialog.Builder(this)
            .setTitle("Main Menu Help")
            .setMessage("Welcome to Space Colony! Click on the rooms to manage your base:\n\n- Medical: Heal injured crew.\n- Training: Level up skills.\n- Control: Start missions.\n- Team: View your crew.\n- Recruitment: Get new members.\n- Record: View stats.")
            .setPositiveButton("Got it", null)
            .show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupArea(int id, Class<?> target) {
        ImageView iv = findViewById(id);
        if (iv == null) return;

        iv.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && isPixelTransparent(v, event)) {
                v.performClick();
                startActivity(new Intent(this, target));
                return true;
            }
            return false;
        });
    }

    private boolean isPixelTransparent(View v, MotionEvent e) {
        Drawable d = ((ImageView) v).getDrawable();
        if (d == null) return false;

        Bitmap b;
        if (d instanceof BitmapDrawable) b = ((BitmapDrawable) d).getBitmap();
        else {
            b = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(b);
            d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            d.draw(canvas);
        }

        int x = (int) (e.getX() * b.getWidth() / v.getWidth());
        int y = (int) (e.getY() * b.getHeight() / v.getHeight());
        
        if (x < 0 || x >= b.getWidth() || y < 0 || y >= b.getHeight()) return false;
        return Color.alpha(b.getPixel(x, y)) > 0;
    }
}
