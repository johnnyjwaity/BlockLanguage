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



    private ParameterHolder p1, p2;
    private Spinner operator;

    public void setP1(ParameterHolder p1) {
        this.p1 = p1;
    }

    public void setP2(ParameterHolder p2) {
        this.p2 = p2;
    }

    public void setOperator(Spinner operator) {
        this.operator = operator;
    }

    public static OperatorBlock create(){
        ParameterHolder value1 = new ParameterHolder(height);
        ParameterHolder value2 = new ParameterHolder(height);

        Spinner op = new Spinner(MainActivity.sharedInstance.getBaseContext());
        String[] items = new String[]{"+", "-", "*", "/", "^"};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 52);
        params.setMargins(0, 0, 0 , 0);
        op.setPadding(0, 0, 0, 0);
        op.setLayoutParams(params);
        op.setAdapter(new ArrayAdapter<String>(MainActivity.sharedInstance, android.R.layout.simple_spinner_dropdown_item, items));
        OperatorBlock n = new OperatorBlock(new View[]{value1, op, value2});
        n.setP1(value1);
        n.setP2(value2);
        n.setOperator(op);
        return n;
    }






    @Override
    public ParamValue getValue() {
        ParamValue val = p1.getValue();
        ParamValue val2 = p2.getValue();
        switch(operator.getSelectedItem().toString()){
            case "+":
                switch(val.getDataType()){
                    case Number:
                        switch (val2.getDataType()){
                            case Number:
                                ParamValue p = new ParamValue(DataType.Number);
                                p.setNumValue(val.getNumValue() + val2.getNumValue());
                                return p;
                        }
                        break;

                }
                break;
        }
        return null;
    }

}
