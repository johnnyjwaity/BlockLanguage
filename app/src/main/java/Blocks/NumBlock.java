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



    private EditText editTextValue;
    public void setEditTextValue(EditText val){
        editTextValue = val;
    }


    public static NumBlock create(){
        EditText value = new EditText(MainActivity.sharedInstance.getBaseContext());
        value.setBackgroundColor(Color.rgb(150, 150, 255));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 30);
        params.setMargins(0, 10, 0 , 0);
        value.setLayoutParams(params);
        value.setPadding(0, 5, 0, 0);
        value.setTextSize(18);

        TextView Number = new TextView(MainActivity.sharedInstance.getBaseContext());
        Number.setText("Num");
        Number.setTextColor(Color.WHITE);
        Number.setTextSize(18);
        NumBlock n = new NumBlock(new View[]{Number, value});
        n.setEditTextValue(value);
        return n;
    }

    @Override
    public String getJSValue() {
        return editTextValue.getText().toString();
    }
}
