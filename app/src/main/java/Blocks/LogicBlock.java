package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

        final Spinner op = new Spinner(MainActivity.sharedInstance.getBaseContext());
        op.setScaleX(1.3f);
        op.setScaleY(1.3f);
        String[] items = new String[]{"and", "or", "==", "!=", ">", ">=", "<", "<="};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 52);
        params.setMargins(0, 0, 0 , 0);
        op.setPadding(0, 0, 0, 0);
        op.setLayoutParams(params);
        op.setAdapter(new ArrayAdapter<String>(MainActivity.sharedInstance, android.R.layout.simple_spinner_dropdown_item, items));
        LogicBlock n = new LogicBlock(new View[]{value1, op, value2});
        n.setP1(value1);
        n.setP2(value2);
        n.setOperator(op);
        n.addParamHolder(value1);
        n.addParamHolder(value2);

        op.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) op.getSelectedView()).setTextColor(Color.WHITE);
            }
        });
        return n;
    }



    @Override
    public String getJSValue() {
        String op = operator.getSelectedItem().toString();
        if (op.equals("and")) {
            op = "&&";
        } else if (op.equals("or")) {
            op = "||";
        }

        return p1.getJSValue() + " " + op + " " + p2.getJSValue();
    }
}
