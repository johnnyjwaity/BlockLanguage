package Blocks;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Block extends RelativeLayout {


    private View[] subviews;

    private float lastX;
    private float lastY;
    private int height;

    private ArrayList<ParamBlock> subParamBlocks = new ArrayList<>();

    public Block(View[] subviews, int height){
        super(MainActivity.sharedInstance.getBaseContext());
        this.subviews = subviews;
        this.height = height;
        populate();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void populate(){
//        Block block = this;
        this.setBackgroundColor(Color.rgb(255, 153, 0));
        LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setMinimumHeight(height);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.setLayoutParams(p);
        LinearLayout layout = new LinearLayout(MainActivity.sharedInstance.getBaseContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setMinimumHeight(height);
        this.addView(layout);

        ImageView drag = new ImageView(MainActivity.sharedInstance.getBaseContext());
        drag.setImageDrawable(this.getContext().getDrawable(R.drawable.drag));
        LinearLayout.LayoutParams dragParams = new LinearLayout.LayoutParams((int) (0.75 * height), (int) (0.75 * height));

        dragParams.setMargins(((int) (0.07 * height)), 12, 10, 0);
        drag.setLayoutParams(dragParams);
        drag.setAlpha(0.3f);
        drag.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    translate(event.getRawX() - lastX, event.getRawY() - lastY);
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    breakSnap();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    snap();
                }
                return true;
            }
        });
        layout.addView(drag);
        for(View v : subviews){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
            if(params == null){
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                v.setLayoutParams(params);
            }
            params.setMargins(15, params.topMargin, 15 ,params.bottomMargin);

            layout.addView(v);
        }


    }

    public void setColor(int color){
        this.setBackgroundColor(color);
    }
    public void setBackgroundImage(Drawable d){
        this.setBackground(d);
    }

    public void setNewLayoutParams(LayoutParams p) { this.setLayoutParams(p); }

    public void translate(float x, float y){
        bringToFront();
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
        for (ParamBlock block: subParamBlocks) {
            block.translate(x, y);
        }
    }

    public void removeSubParam(ParamBlock paramB) {
        if (subParamBlocks.indexOf(paramB) >= 0) {
            subParamBlocks.remove(paramB);
            //Code I tried doing to make the width of the parent block ajust to having a new paramter
//            getLayoutParams().width -= paramB.getWidth()-150;
//            paramB.getParentEditText().getLayoutParams().width = 150;
        }
    }

    public void addSubParam(ParamBlock paramB) {
        subParamBlocks.add(paramB);
    }

    public View[] getSubviews() {
        return subviews;
    }


    public abstract void snap();
    public abstract void breakSnap();



}
