package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

public class WhileLoop extends EnclosureBlock {
    private ParameterHolder boolValue;

    public void setBoolValue(ParameterHolder boolValue) {
        this.boolValue = boolValue;
    }

    public WhileLoop(View[] subviews) {
        super(subviews);
    }

    public static WhileLoop create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("while");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder parameterHolder = new ParameterHolder(height);
        WhileLoop b =  new WhileLoop(new View[]{var, parameterHolder});
        b.setBoolValue(parameterHolder);
        b.addParamHolder(parameterHolder);
        return b;
    }

    public static WhileLoop create(InlineBlock[] subBlocks){
        final WhileLoop block = create();
        block.setInsidePreset(subBlocks);
        return block;
    }

    @Override
    public String getJSValue() {
        return "while(" + boolValue.getJSValue() + "){" + getInsideJS() + "}";
    }
}
