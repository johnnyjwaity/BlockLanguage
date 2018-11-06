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

public class NumBlock extends ParamBlock {

    public NumBlock(View[] subviews){
        super(subviews);
    }



    private TextView editTextValue;
    public void setEditTextValue(TextView val){
        editTextValue = val;
    }


    public static NumBlock create(){
        final TextView value = new TextView(MainActivity.sharedInstance.getBaseContext());
//        value.setText("Hello");
        value.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 5, 10 , 0);
        value.setLayoutParams(params);
        value.setTextSize(15);
        value.setTextColor(Color.BLACK);
        value.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.sharedInstance);
                builder.setTitle("Enter Number");
                final EditText input = new EditText(MainActivity.sharedInstance);
                input.setText(value.getText().toString());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        value.setText(input.getText().toString());
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


        TextView Number = new TextView(MainActivity.sharedInstance.getBaseContext());
        Number.setText("Num");
        Number.setTextColor(Color.WHITE);
        Number.setTextSize(18);
        NumBlock n = new NumBlock(new View[]{Number, value});
        n.setEditTextValue(value);
        return n;
    }

    @Override
    public String getJSValue() {
        return editTextValue.getText().toString();
    }
}
