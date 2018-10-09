package com.johnnywaity.blocklanguage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Blocks.Block;
import Blocks.DeclareVariable;
import Blocks.ElseBlock;
import Blocks.EnclosureBlock;
import Blocks.FalseBlock;
import Blocks.FollowBlock;
import Blocks.GetVarBlock;
import Blocks.IfBlock;
import Blocks.InlineBlock;
import Blocks.LogicBlock;
import Blocks.OperatorBlock;
import Blocks.ParamBlock;
import Blocks.NumBlock;
import Blocks.PrintBlock;
import Blocks.StartBlock;
import Blocks.StringBlock;
import Blocks.TrueBlock;
import Blocks.WhileLoop;
import Runtime.Interpreter;

public class MainActivity extends AppCompatActivity {

    public static MainActivity sharedInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedInstance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        populateMenu();

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
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
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
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return null;

//        String ret = "";
//
//        try {
//            InputStream inputStream = context.openFileInput("config.txt");
//
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    System.out.println("Line: "+receiveString);
//                    stringBuilder.append(receiveString);
//                }
//
//                inputStream.close();
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        }
//
//        return ret;
    }

    private void populateMenu(){
        Class[] blocks = {StartBlock.class, DeclareVariable.class, PrintBlock.class, NumBlock.class, StringBlock.class,
                GetVarBlock.class, OperatorBlock.class, LogicBlock.class, TrueBlock.class, FalseBlock.class, IfBlock.class, ElseBlock.class, WhileLoop.class};
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
