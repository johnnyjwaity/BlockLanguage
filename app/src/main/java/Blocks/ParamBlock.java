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

import java.util.HashMap;
import java.util.Map;

public abstract class ParamBlock extends Block {

    private final static float MAX_CLIP_DISTANCE = 50;


    private float lastX;
    private float lastY;

    public ParamBlock(View[] subviews){
        super(subviews);
        super.setColor(Color.rgb(0, 80, 240));
    }

    @Override
    public void snap(){

    }
    @Override
    public void breakSnap(){

    }


}
