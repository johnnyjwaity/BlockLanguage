package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;


public class PrintBlock extends InlineBlock {

    public PrintBlock(View[] subviews){
        super(subviews);

    }


    @Override
    public void execute() {

    }

    public static PrintBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("print");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        EditText value = new EditText(MainActivity.sharedInstance.getBaseContext());
        value.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 52);
        params.setMargins(0, 10, 0 , 0);
        value.setLayoutParams(params);


        return new PrintBlock(new View[]{var, value});
    }
}
