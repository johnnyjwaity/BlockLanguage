package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class WhileLoop extends EnclosureBlock {
    private ParameterHolder boolValue;

    public void setBoolValue(ParameterHolder boolValue) {
        this.boolValue = boolValue;
    }

    public WhileLoop(View[] subviews) {
        super(subviews);
    }

    @Override
    public void execute() {
        while (boolValue.getValue().getBool()){
            executeInside();
        }
    }

    public static WhileLoop create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("while");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder parameterHolder = new ParameterHolder(height);
        WhileLoop b =  new WhileLoop(new View[]{var, parameterHolder});
        b.setBoolValue(parameterHolder);
        return b;
    }
}
