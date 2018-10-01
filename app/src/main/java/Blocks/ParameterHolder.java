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

//    private static List<ParameterHolder> paramHolders;

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
//        this.setGravity(Gravity.LEFT);
//        addParamHolder(this);





    }

//    public static List<ParameterHolder> getParamHolders() {
//        if(paramHolders == null){
//            paramHolders = new ArrayList<>();
//        }
//        return paramHolders;
//    }
//
//    public static void addParamHolder(ParameterHolder p) {
//        if(paramHolders == null){
//            paramHolders = new ArrayList<>();
//            paramHolders.add(p);
//        }else{
//            paramHolders.add(p);
//        }
//    }



}
