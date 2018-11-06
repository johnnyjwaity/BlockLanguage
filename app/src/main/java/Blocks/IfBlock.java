package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

public class IfBlock extends EnclosureBlock {

    private ParameterHolder boolValue;

    public void setBoolValue(ParameterHolder boolValue) {
        this.boolValue = boolValue;
    }

    public IfBlock(View[] subviews) {
        super(subviews);
    }




    public static IfBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("if");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        ParameterHolder parameterHolder = new ParameterHolder(height);
        IfBlock b =  new IfBlock(new View[]{var, parameterHolder});
        b.setBoolValue(parameterHolder);
        b.addParamHolder(parameterHolder);
        return b;
    }

    public static IfBlock create(InlineBlock[] subBlocks){
        final IfBlock block = create();
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


    @Override
    public String getJSValue() {
        return "if(" + boolValue.getJSValue() + "){" + getInsideJS() + "}";
    }
}
