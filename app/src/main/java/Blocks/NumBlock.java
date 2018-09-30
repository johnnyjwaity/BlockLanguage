package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class NumBlock extends ParamBlock {

    public NumBlock(View[] subviews){
        super(subviews);
    }

    @Override
    public void execute() {

    }

    public static NumBlock create(){
        EditText value = new EditText(MainActivity.sharedInstance.getBaseContext());
        value.setBackgroundColor(Color.rgb(150, 150, 255));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 52);
        params.setMargins(0, 10, 0 , 0);
        value.setLayoutParams(params);
//        value.setTextSize(61);
//        value.setText("1-00dsfi0");

        TextView Number = new TextView(MainActivity.sharedInstance.getBaseContext());
        Number.setText("Num");
        Number.setTextColor(Color.WHITE);
        Number.setTextSize(21);
        return new NumBlock(new View[]{Number, value});
    }
}
