package Blocks;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.os.SystemClock;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParamBlock extends Block {

    private final static float MAX_CLIP_DISTANCE = 50;

    private Block parentSnapView = null;
    private EditText parentEditText = null;

    private float lastX;
    private float lastY;
    public final static int height = 52;

    public ParamBlock(View[] subviews){
        super(subviews, height);
        Drawable d = MainActivity.sharedInstance.getDrawable(R.drawable.rounded_rect);
        d.setTint(Color.rgb(0, 80, 240));
        super.setBackgroundImage(d);
        this.setPadding(5 ,0 , 5, 0);
//        super.setColor(Color.rgb(0, 80, 240));
//        LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 70);
//        p.addRule(RelativeLayout.CENTER_IN_PARENT);
//        super.setNewLayoutParams(p);
    }

    @Override
    public void snap(){
        ParamBlock block = this;
        Map<Float, ParameterHolder> distances = new HashMap<>();
//        List<ParameterHolder> children = ParameterHolder.getParamHolders();
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
//        System.out.println("paramHolderAmount " + children.size());


        List<View> allChildren = getAllChildren(workflow);
        for (View view: allChildren) {
            if (view instanceof ParameterHolder) {
//                        System.out.println("Found");
                int[] coords = new int[2];
                view.getLocationInWindow(coords);
                float distance = (float) Math.sqrt(Math.pow(block.getX() - coords[0], 2) + Math.pow(block.getY() - coords[1]+50, 2));
                if (distance <= MAX_CLIP_DISTANCE) {
                    distances.put(distance, (ParameterHolder) view);
                }
//                System.out.println(distance);
//                System.out.println(coords[0]);
            }
        }
//        for(int i = 0; i < workflow.getChildCount(); i++) {
//            View child = workflow.getChildAt(i);
//            if (child.equals(block)) {
//                continue;
//            }
//            if (child instanceof Block) {
//                for (View view : ((Block) child).getSubviews()) {
////                    System.out.println(view.getClass());
//                    if (view instanceof ParameterHolder) {
////                        System.out.println("Found");
//                        int[] coords = new int[2];
//                        view.getLocationInWindow(coords);
//                        float distance = (float) Math.sqrt(Math.pow(block.getX() - coords[0], 2) + Math.pow(block.getY() - coords[1], 2));
//                        if (distance <= MAX_CLIP_DISTANCE) {
//                            distances.put(distance, (ParameterHolder) view);
//                        }
//                        System.out.println(distance);
//                        System.out.println(coords[0]);
//                    }
//                }
//            }
//        }

        if(!distances.isEmpty()){
            float closestDistance = (Float) distances.keySet().toArray()[0];
            ParameterHolder closestView = distances.get(closestDistance);
            for(Float distance : distances.keySet()){
                if(distance < closestDistance){
                    closestDistance = distance;
                    closestView = distances.get(distance);
                }
            }
            ViewGroup v = (ViewGroup) block.getParent();
            v.removeView(block);
            int originalBlockWidth = block.getWidth();
            int originalBlockHeight = block.getHeight();
            block.setLeft(0);
            block.setX(0);
            block.setTop(0);
            block.setY(0);
            closestView.addView(block);
            ViewGroup lastView = closestView;
            Block lastBlock = this;
            while(true){
                if (lastView.equals(workflow)){
                    break;
                }
                if(lastView instanceof Block){
                    lastBlock = (Block) lastView;
                }
                lastView = (ViewGroup) lastView.getParent();
            }
            float changeInWidth = originalBlockWidth - closestView.getMinimumWidth();
            lastBlock.setX(lastBlock.getX()+ (changeInWidth / 2));
            updateBlockHeight(2, lastBlock.getHeight(), lastBlock);
        }
    }

    public void updateBlockHeight(final int delay, final float oldHeight, final Block view) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        float changeInHeight = view.getHeight() - oldHeight;
                        view.setY(view.getY()+ (changeInHeight / 2));
                        if (view instanceof InlineBlock && ((InlineBlock) view).getSnappedView() != null) {
                            ((InlineBlock) view).getSnappedView().translate(0, changeInHeight);
                        }

                    }
                },
                delay);
    }

    @Override
    public void breakSnap(){
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        LinearLayout menu = MainActivity.sharedInstance.findViewById(R.id.MenuList);

        ViewGroup parent = (ViewGroup) this.getParent();
        if(!parent.equals(workflow) && !parent.equals(menu)){

            float[] coords = new float[]{this.getX(), this.getY()};
            View lastViewForCoords = parent;
            Block lastBlock = this;
            while (!lastViewForCoords.equals(workflow)){
                if (lastViewForCoords instanceof Block) {
                    lastBlock = (Block) lastViewForCoords;
                }
                coords[0]+=lastViewForCoords.getX();
                coords[1]+=lastViewForCoords.getY();
                lastViewForCoords = (View) lastViewForCoords.getParent();
            }
//            this.getLocationOnScreen(coords);
            parent.removeView(this);
//            this.setX(coords[0]-874);
//            this.setY(coords[1]-669);
            updateCoords(1, this, coords);
            workflow.addView(this);

            float changeInWidth = this.getWidth() - parent.getMinimumWidth();
            lastBlock.setX(lastBlock.getX() - (changeInWidth / 2));
            updateBlockHeight(2, lastBlock.getHeight(), lastBlock);
        }
    }

    public void updateCoords(final int delay, final View view, final float[] coords) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        view.setX(coords[0]);
                        view.setY(coords[1]);

                    }
                },
                delay);
    }

    public EditText getParentEditText() {
        return parentEditText;
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

    public abstract ParamValue getValue();
}
