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
        import java.util.List;
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
//        for(int i = 0; i < workflow.getChildCount(); i++){
//            View child = workflow.getChildAt(i);
//            if(child.equals(block)){
//                continue;
//            }
//            if(child instanceof InlineBlock){
//                float distance = (float) Math.sqrt(Math.pow(block.getX() - child.getX(), 2) + Math.pow(block.getY() - (child.getY() + (child.getHeight() * child.getScaleY())), 2));
//                if(distance <= MAX_CLIP_DISTANCE){
//                    distances.put(distance, child);
//                }
//            }
//        }
        for(View child : getAllChildren(workflow)){
            if(child.equals(block)){
                continue;
            }
            if(child instanceof InlineBlock){
                int[] coords = new int[2];
                child.getLocationInWindow(coords);
                int childX = coords[0];
                int childY = coords[1];
//                float distance = (float) Math.sqrt(Math.pow(block.getX() - childX, 2) + Math.pow(block.getY() - (childY + (child.getHeight() * child.getScaleY())), 2));
                float distance = (float) Math.sqrt(Math.pow(block.getX() - coords[0], 2) + Math.pow(block.getY() - coords[1] - child.getHeight(), 2));
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
            if(closestView instanceof InlineBlock){
                snapToBlock((InlineBlock) closestView);
            }
        }
    }

    public void snapToBlock(InlineBlock b){
        int[] coords = new int[2];
        b.getLocationOnScreen(coords);
        this.translate(coords[0] - this.getX(), coords[1] + (b.getHeight() - this.getY()));
//                block.translate(0, -(((float)block.getHeight()) / 2) - 3);
        this.translate(0, -49);
        InlineBlock parentView = (InlineBlock) b;
        parentView.setSnappedView(this);
        parentSnapView = parentView;
    }

    @Override
    public void breakSnap(){
        if(parentSnapView != null){
            if(this.equals(parentSnapView.getSnappedView())){
                parentSnapView.setSnappedView(null);
            }
            parentSnapView = null;
        }
    }

    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();
        result.add(v);

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }


}
