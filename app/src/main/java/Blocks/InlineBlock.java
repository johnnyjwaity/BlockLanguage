package Blocks;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

public abstract class InlineBlock extends RelativeLayout {

    private View[] subviews;

    private float lastX;
    private float lastY;

    public InlineBlock(View[] subviews){
        super(MainActivity.sharedInstance.getBaseContext());
        this.subviews = subviews;
        populate();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void populate(){
        this.setBackgroundColor(Color.rgb(255, 153, 0));
        LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.setLayoutParams(p);
        LinearLayout layout = new LinearLayout(MainActivity.sharedInstance.getBaseContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(layout);
        ImageView drag = new ImageView(MainActivity.sharedInstance.getBaseContext());
        drag.setImageDrawable(this.getContext().getDrawable(R.drawable.drag));
        LinearLayout.LayoutParams dragParams = new LinearLayout.LayoutParams(75, 75);
        dragParams.setMargins(5, 12, 10, 0);
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
                }
                return true;
            }
        });
        layout.addView(drag);

        for(View v : subviews){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
            if(params == null){
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.setMargins(30, params.topMargin, 30 ,params.bottomMargin);

            layout.addView(v);
        }
        this.setScaleX(0.7f);
        this.setScaleY(0.7f);
    }

    private void translate(float x, float y){
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    public abstract void execute();
}
