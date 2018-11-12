package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

public class ElseBlock extends EnclosureBlock {
    public ElseBlock(View[] subviews) {
        super(subviews);
    }


    @Override
    public String getJSValue() {
        return "else{"+getInsideJS()+"}";
    }

    public static ElseBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("else");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);
        return new ElseBlock(new View[]{var});
    }

    public static ElseBlock create(InlineBlock[] subBlocks){
        final ElseBlock block = create();
        block.setInsidePreset(subBlocks);
        return block;
    }
}
