package com.johnnywaity.blocklanguage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngineManager;

import Blocks.Block;
import Blocks.DeclareVariable;
import Blocks.ElseBlock;
import Blocks.FalseBlock;
import Blocks.GetVarBlock;
import Blocks.IfBlock;
import Blocks.InlineBlock;
import Blocks.LogicBlock;
import Blocks.OperatorBlock;
import Blocks.NumBlock;
import Blocks.PrintBlock;
import Blocks.StartBlock;
import Blocks.StringBlock;
import Blocks.TrueBlock;
import Blocks.WhileLoop;
import Gameplay.GameplayManager;
import Gameplay.QuestionBase;
import Gameplay.QuestionParameter;
import Runtime.Interpreter;

public class MainActivity extends AppCompatActivity {

    public static MainActivity sharedInstance;

    private float lastX;
    private float lastY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedInstance = this;
        Interpreter.scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TextView console = findViewById(R.id.console);
        console.setVisibility(View.INVISIBLE);
        findViewById(R.id.MenuList).bringToFront();

        Button runButton = findViewById(R.id.runButton);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout workflow = findViewById(R.id.Workflow);
                StartBlock startBlock = null;
                for(int i = 0; i < workflow.getChildCount(); i ++){
                    View child = workflow.getChildAt(i);
                    if(child instanceof StartBlock){
                        startBlock = (StartBlock) child;
                    }
                }
                Interpreter interpreter = new Interpreter(startBlock);
                interpreter.run();

//                ArrayList<Block> blocks = new ArrayList<>();
//                for (View child: getAllChildren(findViewById(R.id.Workflow))) {
//                    if (child instanceof Block) {
//                        blocks.add((Block) child);
//                    }
//                }

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                writeToFile(((TextView)findViewById(R.id.console)).getText(), sharedInstance);
                                System.out.println(readFromFile(sharedInstance));
                            }
                        },
                        5);
            }
        });
        final GameplayManager m = new GameplayManager();


        final RelativeLayout workflow = findViewById(R.id.Workflow);
        workflow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    for (int i = 0; i < workflow.getChildCount(); i ++){
                        View child = workflow.getChildAt(i);
                        child.setX(child.getX() + (event.getRawX() - lastX));
                        child.setY(child.getY() + (event.getRawY() - lastY));
                    }
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }
                return true;
            }
        });

        ((Button) findViewById(R.id.hintButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint = m.getHint();
                ConstraintLayout root = (ConstraintLayout) v.getParent();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                MainActivity.sharedInstance.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screen_height = displayMetrics.heightPixels;
                int screen_width = displayMetrics.widthPixels;
                int width = 800;
                int height = 350;

                RelativeLayout box = new RelativeLayout(MainActivity.sharedInstance);
                box.setBackground(getDrawable(android.R.drawable.alert_dark_frame));
                box.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                box.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_orange_light, MainActivity.sharedInstance.getTheme())));
                box.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));
                root.addView(box);
                box.setX((screen_width / 2) - width / 2);
                box.setY((screen_height / 2) - height / 2);

                TextView hintText = new TextView(MainActivity.sharedInstance);
                hintText.setTextColor(Color.WHITE);
                hintText.setTextSize(26);
                hintText.setTypeface(Typeface.DEFAULT_BOLD);
                hintText.setText(hint);
                hintText.setGravity(Gravity.CENTER);
                hintText.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                box.addView(hintText);
                hintText.setLeft(0);
                hintText.setTop(0);

                TextView closeText = new TextView(MainActivity.sharedInstance);
                closeText.setTextColor(Color.WHITE);
                closeText.setTextSize(16);
                closeText.setTypeface(Typeface.DEFAULT_BOLD);
                closeText.setText("Touch To Close");
                closeText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                closeText.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
                box.addView(closeText);
                closeText.setLeft(0);
                closeText.setTop(0);

                box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout root = (ConstraintLayout) v.getParent();
                        root.removeView(v);
                    }
                });
            }
        });


    }

    public void clickConsole(View view) {
        final TextView console = findViewById(R.id.console);
        console.setVisibility(View.VISIBLE);
        console.setText(readFromFile(sharedInstance));
        console.bringToFront();

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    sharedInstance.findViewById(R.id.MenuList).bringToFront();
                    console.setVisibility(View.INVISIBLE);
                }
            },
            5000);
    }



    private void writeToFile(Object data, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("config.txt", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput("config.txt");
            ObjectInputStream is = new ObjectInputStream(fis);
            String simpleClass = (String) is.readObject();
            is.close();
            fis.close();
            return simpleClass;
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return null;
    }

    public void populateMenu(int level){
        LinearLayout menu = findViewById(R.id.MenuList);
        menu.removeAllViews();

        Class[][] levelBlocks = {{StartBlock.class, PrintBlock.class, StringBlock.class, NumBlock.class}, {DeclareVariable.class, GetVarBlock.class, OperatorBlock.class},
                {IfBlock.class, ElseBlock.class, LogicBlock.class, TrueBlock.class, FalseBlock.class}, {WhileLoop.class}};
        for(int i = 0; i < level; i++){
            for (Class block : levelBlocks[i]){
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

                    menu.addView(b);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) b.getLayoutParams();
                    params.setMargins(20, 15, 0, 15);
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

    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();
        result.add(v);

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }
}
