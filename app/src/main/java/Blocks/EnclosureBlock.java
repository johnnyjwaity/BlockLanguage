package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class EnclosureBlock extends InlineBlock {

    private InlineHolder holder;

    public EnclosureBlock(View[] subviews) {
        super(subviews);
        setColor(Color.rgb(0, 165, 114));
        System.out.println(getWidth());

        final InlineHolder holder = new InlineHolder();
        ((ViewGroup)getChildAt(0)).addView(holder);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        holder.setMinimumWidth(getWidth());
                    }
                },
                2);
        this.holder = holder;

    }

    @Override
    public void execute() {

    }

    public static EnclosureBlock create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("var");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        EditText varName = new EditText(MainActivity.sharedInstance.getBaseContext());
        varName.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 52);
        params.setMargins(0, 10, 0 , 0);
        varName.setPadding(0, 0, 0, 0);
        varName.setLayoutParams(params);
        varName.setTextSize(20);

        TextView equalSign = new TextView(MainActivity.sharedInstance.getBaseContext());
        equalSign.setText("=");
        equalSign.setTextColor(Color.WHITE);
        equalSign.setTextSize(21);

        ParameterHolder value = new ParameterHolder(height);




        return new EnclosureBlock(new View[]{var, varName, equalSign, value});
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        holder.setMinimumWidth(w);
        System.out.println("SizeChnage");
    }
}
