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
        String[] items = new String[]{"+", "-", "*", "/", "%", "^"};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 52);
        params.setMargins(0, 0, 0 , 0);
        op.setPadding(0, 0, 0, 0);
        op.setLayoutParams(params);
        op.setAdapter(new ArrayAdapter<String>(MainActivity.sharedInstance, android.R.layout.simple_spinner_dropdown_item, items));
        OperatorBlock n = new OperatorBlock(new View[]{value1, op, value2});
        n.setP1(value1);
        n.setP2(value2);
        n.setOperator(op);
        n.addParamHolder(value1);
        n.addParamHolder(value2);
        return n;
    }



    @Override
    public String getJSValue() {
        if(operator.getSelectedItem().toString().equals("^")){
            return "Math.pow(" + p1.getJSValue() + ", " + p2.getJSValue() + ")";
        }
        return p1.getJSValue() + " " + operator.getSelectedItem().toString() + " " + p2.getJSValue();
    }
}
