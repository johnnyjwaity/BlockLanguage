package Gameplay;

import android.view.View;
import android.widget.RelativeLayout;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import java.util.ArrayList;
import java.util.List;

import Blocks.Block;
import Blocks.InlineBlock;

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
        final InlineBlock[] blocks = getPreset();
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout workflow = MainActivity.sharedInstance.findViewById(R.id.Workflow);
                workflow.removeAllViews();
                InlineBlock prevBlock = null;
                for (InlineBlock b : blocks){
                    workflow.addView(b);
                    if(prevBlock != null){
                        final InlineBlock first = prevBlock;
                        final InlineBlock last = b;
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        last.snapToBlock(first);
                                    }
                                },
                                1000);

                    }
                    prevBlock = b;
                }



            }
        });
    }


    public abstract String setQuestionBase();
    public abstract String getAnswer(List<String> values);
    public abstract InlineBlock[] getPreset();
}
