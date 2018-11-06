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
        final InlineBlock[] subs = subBlocks;
        final RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        for (InlineBlock b : subs){
                            final InlineBlock b2 = b;
                            workflow.addView(b2);
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            b2.snapToBlock(block.getHolder().getFollowBlock());
                                        }
                                    },
                                    100);
                        }
                    }
                },
                1000);

        return block;
    }
}
