package Blocks;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.WrapperListAdapter;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import java.util.ArrayList;
import java.util.List;

public class ParameterHolder extends RelativeLayout {


    public ParameterHolder (int height){
        super(MainActivity.sharedInstance.getBaseContext());
        Drawable back = MainActivity.sharedInstance.getDrawable(R.drawable.rounded_rect);
        back.setTint(Color.WHITE);
        this.setBackground(back);
        LinearLayout.LayoutParams valParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        valParams.setMargins(0, 10, 0 , 10);
        this.setPadding(0, 0 ,0, 0);
        this.setLayoutParams(valParams);
        this.setMinimumWidth(100);
        this.setMinimumHeight(height - 20);
    }

    public String getJSValue(){
        ParamBlock paramBlock = null;
        for(int i = 0; i < this.getChildCount(); i++){
            View child = getChildAt(i);
            if(child instanceof ParamBlock){
                paramBlock = (ParamBlock) child;
                break;
            }
        }
        return paramBlock.getJSValue();
    }



}
