package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;


public class DeclareVariable extends InlineBlock {

    public DeclareVariable(View[] subviews){
        super(subviews);

    }


    @Override
    public void execute() {

    }

    public static DeclareVariable create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("var");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        EditText varName = new EditText(MainActivity.sharedInstance.getBaseContext());
        varName.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 52);
        params.setMargins(0, 10, 0 , 0);
        varName.setLayoutParams(params);

        TextView equalSign = new TextView(MainActivity.sharedInstance.getBaseContext());
        equalSign.setText("=");
        equalSign.setTextColor(Color.WHITE);
        equalSign.setTextSize(21);

//        EditText value = new EditText(MainActivity.sharedInstance.getBaseContext());
//        value.setBackgroundColor(Color.WHITE);
//        LinearLayout.LayoutParams vparams = new LinearLayout.LayoutParams(150, 52);
//        vparams.setMargins(0, 10, 0 , 0);
//        value.setLayoutParams(vparams);

        ParameterHolder value = new ParameterHolder(height);



        return new DeclareVariable(new View[]{var, varName, equalSign, value});
    }
}
