package Blocks;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.johnnywaity.blocklanguage.R;

import com.johnnywaity.blocklanguage.MainActivity;

public class InlineHolder extends RelativeLayout {

    private FollowBlock followBlock;

    public InlineHolder() {
        super(MainActivity.sharedInstance.getBaseContext());
        this.setMinimumHeight(InlineBlock.height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT;
        params.setMargins(30, 0, 0, 0);
//        setMinimumWidth(1000);
        setLayoutParams(params);

        this.setBackgroundColor(Color.WHITE);
        followBlock = new FollowBlock();

        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        this.addView(followBlock);


    }

    public void translate(float x, float y){
        followBlock.translate(x, y);
    }

    public FollowBlock getFollowBlock() {
        return followBlock;
    }


}
