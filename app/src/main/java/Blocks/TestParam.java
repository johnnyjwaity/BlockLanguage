package Blocks;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public class TestParam extends ParamBlock {

    public TestParam(View[] subviews) {
        super(subviews);
    }

    public static TestParam create(){

        TextView Number = new TextView(MainActivity.sharedInstance.getBaseContext());
        Number.setText("Num");
        Number.setTextColor(Color.WHITE);
        Number.setTextSize(18);
        TestParam n = new TestParam(new View[]{Number, new ParameterHolder(height)});

        return n;
    }

    @Override
    public ParamBlock cloneParam() {
        return create();
    }

    @Override
    public ParamValue getValue() {
        return null;
    }

    @Override
    public void setParamValue(ParamValue value) {

    }
}
