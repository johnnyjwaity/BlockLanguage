package Blocks;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;


public class DeclareVariable extends InlineBlock {

    private ParameterHolder variableValue = null;
    public void setVariableValue(ParameterHolder block){
        variableValue = block;
    }

    private TextView variableName = null;
    public void setVariableName(TextView variableName) {
        this.variableName = variableName;
    }

    public DeclareVariable(View[] subviews){
        super(subviews);
    }

    @Override
    public String getJSValue() {
        return "var " + variableName.getText().toString() + " = " + variableValue.getJSValue() + ";";
    }

    public static DeclareVariable create(){
        TextView var = new TextView(MainActivity.sharedInstance.getBaseContext());
        var.setText("var");
        var.setTextColor(Color.WHITE);
        var.setTextSize(21);

        final TextView varName = new TextView(MainActivity.sharedInstance.getBaseContext());
        varName.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 52);
        params.setMargins(0, 10, 0 , 0);
        varName.setPadding(0, 0, 0, 0);
        varName.setLayoutParams(params);
        varName.setTextSize(20);
        varName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.sharedInstance);
                builder.setTitle("Enter Variable Name");
                final EditText input = new EditText(MainActivity.sharedInstance);
                input.setText(varName.getText().toString());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        varName.setText(input.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        TextView equalSign = new TextView(MainActivity.sharedInstance.getBaseContext());
        equalSign.setText("=");
        equalSign.setTextColor(Color.WHITE);
        equalSign.setTextSize(21);

        ParameterHolder value = new ParameterHolder(height);



        DeclareVariable d = new DeclareVariable(new View[]{var, varName, equalSign, value});
        d.setVariableValue(value);
        d.setVariableName(varName);

        d.addParamHolder(value);
        return d;
    }

    public void changeVariableName(String name){
        variableName.setText(name);
    }
}
