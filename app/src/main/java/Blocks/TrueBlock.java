package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class TrueBlock extends ParamBlock{

    public TrueBlock(View[] subviews){
        super(subviews);
        setColor(Color.rgb(186,85,211));
    };

    public static TrueBlock create(){
        TextView trueTxt = new TextView(MainActivity.sharedInstance.getBaseContext());
        trueTxt.setText("True");
        trueTxt.setTextColor(Color.WHITE);
        trueTxt.setTextSize(18);
        TrueBlock n = new TrueBlock(new View[]{trueTxt});
        return n;
    }

    @Override
    public ParamValue getValue() {
        ParamValue v = new ParamValue(DataType.Boolean);
        v.setBoolValue(true);
        return v;
    }

    @Override
    public String getJSValue() {
        return "true";
    }
}
