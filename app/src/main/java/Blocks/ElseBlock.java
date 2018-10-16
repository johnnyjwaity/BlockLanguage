package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class ElseBlock extends EnclosureBlock {
    public ElseBlock(View[] subviews) {
        super(subviews);
    }

    @Override
    public void execute() {
        executeInside();
    }

    @Override
    public String getJSValue() {
        return null;
    }

    public static ElseBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("else");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        return new ElseBlock(new View[]{var});
    }
}
