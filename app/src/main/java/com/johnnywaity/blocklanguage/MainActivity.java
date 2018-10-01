package com.johnnywaity.blocklanguage;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Blocks.Block;
import Blocks.DeclareVariable;
import Blocks.InlineBlock;
import Blocks.ParamBlock;
import Blocks.NumBlock;
import Blocks.PrintBlock;
import Blocks.StartBlock;
import Blocks.TestBlock;
import Blocks.TestParam;

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
        Class[] blocks = {StartBlock.class, DeclareVariable.class, PrintBlock.class, NumBlock.class, TestBlock.class, TestParam.class};
        for (Class block : blocks){
            try {
                final Method createMethod = block.getMethod("create", null);
                Block b = (Block) createMethod.invoke(null, null);
                b.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_UP){
                            RelativeLayout workflow = findViewById(R.id.Workflow);
                            try {
                                Block obj = (Block) createMethod.invoke(null, null);
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
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) b.getLayoutParams();
                params.setMargins(8, 15, 0, 15);
                b.setLayoutParams(params);

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
