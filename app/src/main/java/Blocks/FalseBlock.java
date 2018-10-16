package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class FalseBlock extends ParamBlock{
    public FalseBlock(View[] subviews){
        super(subviews);
        setColor(Color.rgb(186,85,211));
    };

    public static FalseBlock create(){
        TextView trueTxt = new TextView(MainActivity.sharedInstance.getBaseContext());
        trueTxt.setText("False");
        trueTxt.setTextColor(Color.WHITE);
        trueTxt.setTextSize(18);
        FalseBlock n = new FalseBlock(new View[]{trueTxt});
        return n;
    }

    @Override
    public ParamValue getValue() {
        ParamValue v = new ParamValue(DataType.Boolean);
        v.setBoolValue(false);
        return v;
    }

    @Override
    public String getJSValue() {
        return "false";
    }
}
