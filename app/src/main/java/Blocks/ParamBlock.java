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

    public ParamBlock(View[] subviews){
        super(subviews, 52);
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



        for(int i = 0; i < workflow.getChildCount(); i++) {
            View child = workflow.getChildAt(i);
            if (child.equals(block)) {
                continue;
            }
            if (child instanceof Block) {
                for (View view : ((Block) child).getSubviews()) {
                    System.out.println(view.getClass());
                    if (view instanceof ParameterHolder) {
                        System.out.println("Found");
                        int[] coords = new int[2];
                        view.getLocationInWindow(coords);
                        float distance = (float) Math.sqrt(Math.pow(block.getX() - coords[0], 2) + Math.pow(block.getY() - coords[1], 2));
                        if (distance <= MAX_CLIP_DISTANCE) {
                            distances.put(distance, (ParameterHolder) view);
                        }
                        System.out.println(distance);
                        System.out.println(coords[0]);
                    }
                }
            }
        }

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

            ParamBlock newBlock = block.cloneParam();
            System.out.println("Create Block With Value " + newBlock.getValue().getNumValue());
            closestView.addView(newBlock);
            boolean foundInlineParent = false;
            ViewGroup lastView = closestView;
            while(!foundInlineParent){
                if(lastView instanceof InlineBlock){
                    foundInlineParent = true;
                }else{
                    lastView = (ViewGroup) lastView.getParent();
                }
            }
            lastView.setX(lastView.getX()+block.getLayoutParams().width/2-closestView.getMinimumWidth());
        }
    }

    @Override
    public void breakSnap(){
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);

        ViewGroup parent = (ViewGroup) this.getParent();
        if(!parent.equals(workflow)){
            int[] coords = new int[]{(int)this.getX(), (int)this.getY()};
//            this.getLocationOnScreen(coords);
            parent.removeView(this);

            workflow.addView(this);
            this.setX(coords[0]);
            this.setY(coords[1]);
        }

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

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }

    public abstract ParamBlock cloneParam();
    public abstract ParamValue getValue();
    public abstract void setParamValue(ParamValue value);
}
