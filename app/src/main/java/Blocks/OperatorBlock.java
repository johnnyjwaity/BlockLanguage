package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class OperatorBlock  extends ParamBlock {

    public OperatorBlock(View[] subviews){
        super(subviews);
    }



    private EditText editTextValue;
    public void setEditTextValue(EditText val){
        editTextValue = val;
    }


    public static NumBlock create(){
        ParameterHolder value1 = new ParameterHolder(height);
        ParameterHolder value2 = new ParameterHolder(height);

        Spinner op = new Spinner(MainActivity.sharedInstance.getBaseContext());
        String[] items = new String[]{"+", "-", "*", "/", "^"};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 52);
        params.setMargins(0, 0, 0 , 0);
        op.setPadding(0, 0, 0, 0);
        op.setLayoutParams(params);
        op.setAdapter(new ArrayAdapter<String>(MainActivity.sharedInstance, android.R.layout.simple_spinner_dropdown_item, items));
//        ((TextView)op.getChildAt(0)).setTextSize(20);
//        op.setTextSize(20);

//        value.setTextSize(61);
//        value.setText("1-00dsfi0");

//        TextView text = new TextView(MainActivity.sharedInstance.getBaseContext());
//        text.setText("Num");
//        text.setTextColor(Color.WHITE);
//        text.setTextSize(18);
        NumBlock n = new NumBlock(new View[]{value1, op, value2});
//        n.setEditTextValue(value);
        return n;
    }






    @Override
    public ParamValue getValue() {
        ParamValue v = new ParamValue(DataType.Number);
        if(editTextValue.getText().toString().equals("")){
            editTextValue.setText("0");
        }
        v.setNumValue(Float.parseFloat(editTextValue.getText().toString()));
        return v;
    }

}
