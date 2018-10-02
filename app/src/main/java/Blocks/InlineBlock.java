package Blocks;

        import android.annotation.SuppressLint;
        import android.graphics.Color;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;

        import com.johnnywaity.blocklanguage.MainActivity;
        import com.johnnywaity.blocklanguage.R;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public abstract class InlineBlock extends Block {

    private final static float MAX_CLIP_DISTANCE = 50;
    public final static int height = 70;

    private InlineBlock snappedView = null;
    private InlineBlock parentSnapView = null;


    @SuppressLint("ClickableViewAccessibility")
    public InlineBlock(View[] subviews){
        super(subviews, height);
    }

    @Override
    public void translate(float x, float y){
        super.translate(x, y);
        if (snappedView != null){
            snappedView.translate(x, y);
        }

    }

    public void setSnappedView(InlineBlock snappedView) {
        this.snappedView = snappedView;
    }

    public InlineBlock getSnappedView() {
        return snappedView;
    }

    public void snap(){
        InlineBlock block = this;
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        Map<Float, View> distances = new HashMap<>();
        for(int i = 0; i < workflow.getChildCount(); i++){
            View child = workflow.getChildAt(i);
            if(child.equals(block)){
                continue;
            }
            if(child instanceof InlineBlock){
                float distance = (float) Math.sqrt(Math.pow(block.getX() - child.getX(), 2) + Math.pow(block.getY() - (child.getY() + (child.getHeight() * child.getScaleY())), 2));
                if(distance <= MAX_CLIP_DISTANCE){
                    distances.put(distance, child);
                }
            }
        }
        if(!distances.isEmpty()){
            float closestDistance = (Float) distances.keySet().toArray()[0];
            View closestView = distances.get(closestDistance);
            for(Float distance : distances.keySet()){
                if(distance < closestDistance){
                    closestDistance = distance;
                    closestView = distances.get(distance);
                }
            }
            block.translate(closestView.getX() - block.getX(), closestView.getY() + (closestView.getHeight() - block.getY()));
            InlineBlock parentView = (InlineBlock) closestView;
            parentView.setSnappedView(block);
            parentSnapView = parentView;
        }
    }

    @Override
    public void breakSnap(){
        if(parentSnapView != null){
            parentSnapView.setSnappedView(null);
            parentSnapView = null;
        }
    }

    public abstract void execute();

}
