package Blocks;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;

public abstract class EnclosureBlock extends InlineBlock {

    private InlineHolder holder;

    public EnclosureBlock(View[] subviews) {
        super(subviews);
        setColor(Color.rgb(0, 165, 114));
        System.out.println(getWidth());
        setPadding(0, 0, 0, 30);

        final InlineHolder holder = new InlineHolder();
        ((ViewGroup)getChildAt(0)).addView(holder);
        this.holder = holder;
        FollowThread followThread = new FollowThread(holder, this);
        followThread.start();
    }



    public String getInsideJS(){
        String total = "";
        InlineBlock lastBlock = holder.getFollowBlock();
        while(lastBlock.getSnappedView() != null){
            lastBlock = lastBlock.getSnappedView();
            total += lastBlock.getJSValue();
        }
        return total;
    }

    @Override
    public void translate(float x, float y) {
        super.translate(x, y);
        holder.translate(x, y);
    }
    public InlineHolder getHolder() {
        return holder;
    }

    private class FollowThread extends Thread {
        private InlineHolder holder;
        private EnclosureBlock block;

        public FollowThread(InlineHolder holder, EnclosureBlock block) {
            this.holder = holder;
            this.block = block;
        }

        public void run(){
            while (true){
                int height = 0;
                InlineBlock currentBlock = holder.getFollowBlock().getSnappedView();
                while (currentBlock != null){
                    height += currentBlock.getHeight();
                    currentBlock = currentBlock.getSnappedView();
                }
                if(height < InlineBlock.height){
                    height = InlineBlock.height;
                }
                if(height != holder.getMinimumHeight()){
                    final int h = height;
                    MainActivity.sharedInstance.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int difference = holder.getMinimumHeight() - h;
                            holder.setMinimumHeight(h);
                            block.setY(block.getY() - difference/2);

                            if(block.getSnappedView() != null){
                                block.getSnappedView().translate(0, -difference);
                            }
                        }
                    });
                }
            }
        }
    }
}
