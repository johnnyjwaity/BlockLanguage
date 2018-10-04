package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
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

        switch(val.getDataType()){
            case Number:
                switch (val2.getDataType()){
                    case Number:
                        ParamValue p = new ParamValue(DataType.Number);
                        switch(operator.getSelectedItem().toString()){
                            case "+":
                                p.setNumValue(val.getNumValue() + val2.getNumValue());
                                return p;
                            case "-":
                                p.setNumValue(val.getNumValue() - val2.getNumValue());
                                return p;
                            case "*":
                                p.setNumValue(val.getNumValue() * val2.getNumValue());
                                return p;
                            case "/":
                                p.setNumValue(val.getNumValue() /+ val2.getNumValue());
                                return p;
                            case "^":
                                p.setNumValue((float) Math.pow(val.getNumValue(), val2.getNumValue()));
                                return p;
                        }
                    case String:
                        ParamValue pString = new ParamValue(DataType.String);
                        pString.setStringValue(val.getRawValue()+val2.getRawValue());
                        return pString;
                }
                break;
            case String:
                switch (val2.getDataType()){
                    case Number:
                        ParamValue p = new ParamValue(DataType.String);
                        p.setStringValue(val.getRawValue()+val2.getRawValue());
                        return p;
                    case String:
                        ParamValue pString = new ParamValue(DataType.String);
                        pString.setStringValue(val.getRawValue()+val2.getRawValue());
                        return pString;
                    case Boolean:
                        ParamValue pBool = new ParamValue(DataType.String);
                        pBool.setStringValue(val.getRawValue()+val2.getRawValue());
                        return pBool;
                }
                break;
            case Boolean:
                ParamValue pString = new ParamValue(DataType.String);
                pString.setStringValue(val.getRawValue()+val2.getRawValue());
                return pString;
        }

        return null;
    }

}
