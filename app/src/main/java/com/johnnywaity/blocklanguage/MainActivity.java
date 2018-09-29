package com.johnnywaity.blocklanguage;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Blocks.DeclareVariable;
import Blocks.InlineBlock;

public class MainActivity extends AppCompatActivity {

    public static MainActivity sharedInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        populateMenu();

    }

    private void populateMenu(){
        Class[] blocks = {DeclareVariable.class};
        for (Class block : blocks){
            try {
                final Method createMethod = block.getMethod("create", null);
                InlineBlock b = (InlineBlock) createMethod.invoke(null, null);
                b.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            RelativeLayout workflow = findViewById(R.id.Workflow);
                            try {
                                InlineBlock obj = (InlineBlock) createMethod.invoke(null, null);
                                obj.setId(View.generateViewId());
                                workflow.addView(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                });
                LinearLayout menu = findViewById(R.id.MenuList);
                menu.addView(b);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
