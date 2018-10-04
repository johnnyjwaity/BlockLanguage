package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

import Runtime.VariableManager;


public class DeclareVariable extends InlineBlock {

    private ParameterHolder variableValue = null;
    public void setVariableValue(ParameterHolder block){
        variableValue = block;
    }

    private EditText variableName = null;
    public void setVariableName(EditText variableName) {
        this.variableName = variableName;
    }

    public DeclareVariable(View[] subviews){
        super(subviews);
    }


    @Override
    public void execute() {
        VariableManager.sharedInstance.setVariable(variableName.getText().toString(), variableValue.getValue());
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
        varName.setPadding(0, 0, 0, 0);
        varName.setLayoutParams(params);
        varName.setTextSize(20);

        TextView equalSign = new TextView(MainActivity.sharedInstance.getBaseContext());
        equalSign.setText("=");
        equalSign.setTextColor(Color.WHITE);
        equalSign.setTextSize(21);

        ParameterHolder value = new ParameterHolder(height);



        DeclareVariable d = new DeclareVariable(new View[]{var, varName, equalSign, value});
        d.setVariableValue(value);
        d.setVariableName(varName);
        return d;
    }
}
