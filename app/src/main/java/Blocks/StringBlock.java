package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class StringBlock extends ParamBlock{

    public StringBlock(View[] subviews){
        super(subviews);
    }



    private EditText editTextValue;
    public void setEditTextValue(EditText val){
        editTextValue = val;
    }


    public static StringBlock create(){
        EditText value = new EditText(MainActivity.sharedInstance.getBaseContext());
        value.setBackgroundColor(Color.rgb(150, 150, 255));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 30);
        params.setMargins(0, 10, 0 , 0);
        value.setLayoutParams(params);
        value.setPadding(0, 5, 0, 0);
        value.setTextSize(18);

//        value.setTextSize(61);
//        value.setText("1-00dsfi0");

        TextView String = new TextView(MainActivity.sharedInstance.getBaseContext());
        String.setText("String");
        String.setTextColor(Color.WHITE);
        String.setTextSize(18);
        StringBlock n = new StringBlock(new View[]{String, value});
        n.setEditTextValue(value);
        return n;
    }






    @Override
    public ParamValue getValue() {
        ParamValue v = new ParamValue(DataType.String);
        if(editTextValue.getText().toString().equals("")){
            editTextValue.setText("0");
        }
        v.setStringValue(editTextValue.getText().toString());
        return v;
    }

    @Override
    public String getJSValue() {
        return "'" + editTextValue.getText().toString() + "'";
    }
}
