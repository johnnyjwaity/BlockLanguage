package Blocks;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import java.util.Map;

public abstract class ParamBlock extends Block {

    private final static float MAX_CLIP_DISTANCE = 50;

    private Block parentSnapView = null;
    private EditText parentEditText = null;

    private float lastX;
    private float lastY;

    public ParamBlock(View[] subviews){
        super(subviews);
        super.setColor(Color.rgb(0, 80, 240));
        LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 70);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        super.setNewLayoutParams(p);
    }

    @Override
    public void snap(){
        ParamBlock block = this;
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        Map<Float, ArrayList<View>> distances = new HashMap<>();
        for(int i = 0; i < workflow.getChildCount(); i++){
            View child = workflow.getChildAt(i);
            if(child.equals(block)){
                continue;
            }
            if(child instanceof Block){
                for (View subChild: ((Block) child).getSubviews()) {
                    if (subChild instanceof EditText) {
                        float distance = (float) Math.sqrt(Math.pow(block.getX() - child.getX()-subChild.getX(), 2) + Math.pow(block.getY() - (subChild.getY()+child.getY()), 2));
                        if(distance <= MAX_CLIP_DISTANCE){
                            ArrayList<View> childWithSubChild = new ArrayList<>();
                            childWithSubChild.add(child);
                            childWithSubChild.add(subChild);
                            distances.put(distance, childWithSubChild);
                        }
                    }
                }
            }
        }
        if(!distances.isEmpty()){
            float closestDistance = (Float) distances.keySet().toArray()[0];
            ArrayList<View> closestView = distances.get(closestDistance);
            for(Float distance : distances.keySet()){
                if(distance < closestDistance){
                    closestDistance = distance;
                    closestView = distances.get(distance);
                }
            }

            //Code I tried doing for the width of the parent blocks to adust, I couldn't get it working
//            closestView.get(1).getLayoutParams().width = getWidth();
//            System.out.println("First: "+closestView.get(0).getWidth()+" "+ closestView.get(0).getX());
//            closestView.get(0).getLayoutParams().width = closestView.get(0).getWidth()+getWidth()-150;
//            System.out.println("Second: "+closestView.get(0).getWidth()+" "+ closestView.get(0).getX());

            block.translate(closestView.get(1).getX()+closestView.get(0).getX() - block.getX(), closestView.get(1).getY()+closestView.get(0).getY()-10 - block.getY());
            Block parentView = (Block) closestView.get(0);
            parentView.addSubParam(block);
            parentSnapView = parentView;
            parentEditText = (EditText) closestView.get(1);
        }
    }

    @Override
    public void breakSnap(){
        if(parentSnapView != null){
            parentSnapView.removeSubParam(this);
            parentSnapView = null;
            parentEditText = null;
        }
    }

    public EditText getParentEditText() {
        return parentEditText;
    }
}
