package Gameplay;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Blocks.Block;
import Blocks.EnclosureBlock;
import Blocks.InlineBlock;
import Blocks.ParamBlock;
import Blocks.ParameterHolder;

public abstract class QuestionBase {

    public String[] getQuestion(QuestionParameter[] params) {
        String base = setQuestionBase();
        List<String> values = new ArrayList<>();
        while (base.contains("<p")){

            int index = Integer.parseInt(base.substring(base.indexOf("<p") + 2, base.indexOf(">")));
            String val = params[index].getValue();
            base = base.replaceFirst("<p" + index + ">", val);
            values.add(val);
        }
        return new String[]{base, getAnswer(values)};
    }

    public void setWorkflow(){
        RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
        workflow.removeAllViews();
        InlineBlock[] preset = getPreset();
        placeInlineBlock(0, preset, workflow, true);
    }
    private void placeInlineBlock(final int index, final InlineBlock[] preset, final RelativeLayout workflow, final boolean canFinish){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                workflow.addView(preset[index]);
                if(index > 0){
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            preset[index].snapToBlock(preset[index - 1]);
                        }
                    }, 50);
                    if(preset[index] instanceof EnclosureBlock){
                        EnclosureBlock b = (EnclosureBlock) preset[index];
                        InlineBlock[] preset2 = new InlineBlock[b.getInsidePreset().length + 1];
                        preset2[0] = b.getHolder().getFollowBlock();
                        for(int i = 0; i < b.getInsidePreset().length; i++){
                            preset2[i + 1] = b.getInsidePreset()[i];
                        }
                        placeInlineBlock(1, preset2, workflow, false);
                    }
                }
                if(index + 1 < preset.length){
                    placeInlineBlock(index + 1, preset, workflow, canFinish);
                }else if(canFinish){
                    Map<ParamBlock, ParameterHolder> map = getParamPreset(preset);
                    ParamBlock[] blocks = new ParamBlock[map.size()];
                    ParameterHolder[] holders = new ParameterHolder[map.size()];
                    int index = 0;
                    for(ParamBlock key : map.keySet()){
                        blocks[index] = key;
                        holders[index] = map.get(key);
                        index ++;
                    }

                    placeParameter(blocks, holders, 0, workflow);
                }
            }
        }, 100);
    }
    private void placeParameter(final ParamBlock[] blocks, final ParameterHolder[] holders, final int index, final RelativeLayout workflow){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                blocks[index].snapToHolder(holders[index]);
                ViewGroup parent = (ViewGroup) blocks[index].getParent();
                if(parent != null){
                    parent.removeView(blocks[index]);
                }
                workflow.addView(blocks[index]);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blocks[index].snapToHolder(holders[index]);
                    }
                }, 50);
                if(index + 1 < blocks.length){
                    placeParameter(blocks, holders, index + 1, workflow);
                }
            }
        }, 100);
    }

    public abstract String setQuestionBase();
    public abstract String getAnswer(List<String> values);
    public abstract InlineBlock[] getPreset();
    public abstract Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks);
    public abstract String getHint();
}
