package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class StartBlock extends InlineBlock {

    public StartBlock(View[] subviews){
        super(subviews);
    }

    @Override
    public void execute() {

    }

    public static StartBlock create(){
        TextView start = new TextView(MainActivity.sharedInstance.getBaseContext());
        start.setText("Start");
        start.setTextColor(Color.WHITE);
        start.setTextSize(21);
        return new StartBlock(new View[]{start});
    }
}
