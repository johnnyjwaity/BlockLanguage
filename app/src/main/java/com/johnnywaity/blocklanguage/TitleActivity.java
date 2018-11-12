package com.johnnywaity.blocklanguage;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

    }

    public void play(){
        Intent t = new Intent(this, MainActivity.class);
        startActivity(t);
    }
}
