package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class TestBlock extends InlineBlock {
    public TestBlock(View[] subviews) {
        super(subviews);
    }

    @Override
    public void execute() {

    }

    public static TestBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("test");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        return new TestBlock(new View[]{var, new ParameterHolder(height), new ParameterHolder(height)});
    }
}
