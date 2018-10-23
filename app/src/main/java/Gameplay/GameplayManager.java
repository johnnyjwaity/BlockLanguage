package Gameplay;

import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

public class GameplayManager {
    public GameplayManager(){
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QuestionBase q = new QuestionBase() {
                    @Override
                    public String setQuestionBase() {
                        return "Print <p0> through <p0>";
                    }
                };

                ((TextView)MainActivity.sharedInstance.findViewById(R.id.questionBox)).setText(q.getQuestion(new QuestionParameter[]{new QuestionParameter() {
                    @Override
                    public String getValue() {
                        return "" + Math.random();
                    }
                }}));
            }
        });
    }




}
