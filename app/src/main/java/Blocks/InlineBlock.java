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

        import java.util.HashMap;
        import java.util.Map;

public abstract class InlineBlock extends RelativeLayout {

    private final static float MAX_CLIP_DISTANCE = 50;

    private View[] subviews;

    private InlineBlock snappedView = null;
    private InlineBlock parentBlock = null;

    private float lastX;
    private float lastY;

    public InlineBlock(View[] subviews){
        super(MainActivity.sharedInstance.getBaseContext());
        this.subviews = subviews;
        populate();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void populate(){
        this.setBackgroundColor(Color.rgb(255, 153, 0));
        LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 70);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.setLayoutParams(p);
        LinearLayout layout = new LinearLayout(MainActivity.sharedInstance.getBaseContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(layout);
        ImageView drag = new ImageView(MainActivity.sharedInstance.getBaseContext());
        drag.setImageDrawable(this.getContext().getDrawable(R.drawable.drag));
        LinearLayout.LayoutParams dragParams = new LinearLayout.LayoutParams(52, 52);
        dragParams.setMargins(5, 12, 10, 0);
        drag.setLayoutParams(dragParams);
        drag.setAlpha(0.3f);
        final InlineBlock block = this;
        drag.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    translate(event.getRawX() - lastX, event.getRawY() - lastY);
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    if(parentBlock != null){
                        parentBlock.removeSnappedView();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
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
                        parentBlock = parentView;
                    }

                }
                return true;
            }
        });
        layout.addView(drag);

        for(View v : subviews){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
            if(params == null){
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                v.setLayoutParams(params);
            }
            params.setMargins(15, params.topMargin, 15 ,params.bottomMargin);

            layout.addView(v);
        }


    }

    private void translate(float x, float y){
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
        if(snappedView != null){
            snappedView.translate(x, y);
        }
    }

    public abstract void execute();

    public InlineBlock getSnappedView() {
        return snappedView;
    }

    public void setSnappedView(InlineBlock snappedView) {
        if(this.snappedView != null){
            this.snappedView.removeParent();
        }

        this.snappedView = snappedView;
    }
    public void removeSnappedView(){
        snappedView = null;
    }
    public void removeParent(){
        parentBlock = null;
    }
}
