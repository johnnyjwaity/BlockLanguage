package Blocks;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;

public class InlineHolder extends RelativeLayout {
    public InlineHolder() {
        super(MainActivity.sharedInstance.getBaseContext());
        this.setMinimumHeight(InlineBlock.height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.setMargins(30, 0, 0, 0);
        setLayoutParams(params);
        this.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setMinimumWidth(int minWidth) {
        super.setMinimumWidth(minWidth - 30);
        super.getLayoutParams().width = minWidth - 30;
    }
}
