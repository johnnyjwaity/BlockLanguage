package Blocks;

import android.graphics.Color;
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.setMargins(30, 0, 0, 0);

        setLayoutParams(params);

        this.setBackgroundColor(Color.WHITE);
        followBlock = new FollowBlock();

        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        this.addView(followBlock);
//        this.setGravity(Gravity.LEFT | Gravity.TOP);

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        int[] coords = new int[2];
//                        getLocationOnScreen(coords);
//                        followBlock.setX(coords[0]);
//                        followBlock.setY(coords[1]);
//                        followBlock.bringToFront();
//                        new android.os.Handler().postDelayed(
//                                new Runnable() {
//                                    public void run() {
//                                        System.out.println("Width " + getMinimumWidth());
//                                        translate(-getWidth(), -(getHeight() * 0.7f));
//                                    }
//                                },
//                                50);
//                    }
//                },
//                5);

    }

    public void translate(float x, float y){
        followBlock.translate(x, y);
    }

    public FollowBlock getFollowBlock() {
        return followBlock;
    }

    @Override
    public void setMinimumWidth(int minWidth) {
        super.setMinimumWidth(minWidth - 30);
//        super.getLayoutParams().width = minWidth - 30;
    }
}
