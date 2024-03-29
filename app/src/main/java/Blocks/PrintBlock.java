package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;


public class PrintBlock extends InlineBlock {

    private ParameterHolder value;

    public void setValue(ParameterHolder value) {
        this.value = value;
    }

    public PrintBlock(View[] subviews){
        super(subviews);

    }




    public static PrintBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("print");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder paramHolder = new ParameterHolder(height);

        PrintBlock p =  new PrintBlock(new View[]{var, paramHolder});
        p.setValue(paramHolder);
        p.addParamHolder(paramHolder);
        return p;
    }

    @Override
    public String getJSValue() {
        return "print(" + value.getJSValue() + ");";
    }
}
