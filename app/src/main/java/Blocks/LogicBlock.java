package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.johnnywaity.blocklanguage.MainActivity;

public class LogicBlock extends ParamBlock {

    public LogicBlock(View[] subviews){
        super(subviews);
        setColor(Color.rgb(186,85,211));
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

    public static LogicBlock create(){
        ParameterHolder value1 = new ParameterHolder(height);
        ParameterHolder value2 = new ParameterHolder(height);

        Spinner op = new Spinner(MainActivity.sharedInstance.getBaseContext());
        String[] items = new String[]{"&&", "||", "==", "!=", ">", ">=", "<", "<="};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 52);
        params.setMargins(0, 0, 0 , 0);
        op.setPadding(0, 0, 0, 0);
        op.setLayoutParams(params);
        op.setAdapter(new ArrayAdapter<String>(MainActivity.sharedInstance, android.R.layout.simple_spinner_dropdown_item, items));
        LogicBlock n = new LogicBlock(new View[]{value1, op, value2});
        n.setP1(value1);
        n.setP2(value2);
        n.setOperator(op);
        return n;
    }

    @Override
    public ParamValue getValue() {
        ParamValue val = p1.getValue();
        ParamValue val2 = p2.getValue();
        ParamValue p = new ParamValue(DataType.Boolean);

        switch(operator.getSelectedItem().toString()){
            case "&&":
                p.setBoolValue(val.getBool() && val2.getBool());
                return p;
            case "||":
                p.setBoolValue(val.getBool() || val2.getBool());
                return p;
            case "==":
                p.setBoolValue(val.getBool() == val2.getBool());
                return p;
            case "!=":
                p.setBoolValue(val.getBool() != val2.getBool());
                return p;
            case "<":
                p.setBoolValue(val.getNumValue() < val2.getNumValue());
                return p;
            case ">":
                p.setBoolValue(val.getNumValue() > val2.getNumValue());
                return p;
            case "<=":
                p.setBoolValue(val.getNumValue() <= val2.getNumValue());
                return p;
            case ">=":
                p.setBoolValue(val.getNumValue() >= val2.getNumValue());
                return p;
        }
        return null;
    }

}
