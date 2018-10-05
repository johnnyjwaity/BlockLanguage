package Blocks;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;

public class FollowBlock extends InlineBlock {

    public FollowBlock() {
        super(new View[0]);
        removeView(getChildAt(0));
        setMinimumHeight(0);
        setMinimumWidth(30);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.setLayoutParams(params);
        setY(0);
        setY(0);
        setLeft(0);
        setTop(0);
    }

    @Override
    public void translate(float x, float y) {
        if(getSnappedView() != null){
            getSnappedView().translate(x, y);
        }

    }

    public void startFollowing(InlineHolder holder){
//        FollowThread followThread = new FollowThread(this, holder);
//        followThread.start();
    }

    @Override
    public void execute() {

    }



}
