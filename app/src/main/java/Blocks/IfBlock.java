package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class IfBlock extends EnclosureBlock {

    private ParameterHolder boolValue;
    private boolean evaledValue = false;

    public void setBoolValue(ParameterHolder boolValue) {
        this.boolValue = boolValue;
    }

    public IfBlock(View[] subviews) {
        super(subviews);
    }

    @Override
    public void execute() {
        if(boolValue.getValue().getBool()){
            executeInside();
            evaledValue = true;
        } else {
            evaledValue = false;
        }
    }

    public boolean getEvaledValue() {
        return evaledValue;
    }

    public static IfBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("if");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder parameterHolder = new ParameterHolder(height);
        IfBlock b =  new IfBlock(new View[]{var, parameterHolder});
        b.setBoolValue(parameterHolder);
        return b;
    }

    @Override
    public String getJSValue() {
        return "";
    }
}
