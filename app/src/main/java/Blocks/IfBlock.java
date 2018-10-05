package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class IfBlock extends EnclosureBlock {

    public IfBlock(View[] subviews) {
        super(subviews);
    }

    @Override
    public void execute() {

    }

    public static IfBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("if");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder parameterHolder = new ParameterHolder(height);
        return new IfBlock(new View[]{var, parameterHolder});
    }
}
