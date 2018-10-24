package Gameplay;

import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameplayManager {

    public static GameplayManager shared;

    private List<QuestionBase> bases = new ArrayList<>();


    public GameplayManager(){
        shared = this;

        bases.add(new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print <p0> through <p1>";
            }

            @Override
            public String getAnswer(List<String> values) {
                String ans = "";
                for (int i = Integer.parseInt(values.get(0)); i <= Integer.parseInt(values.get(1)); i ++){
                    ans += i + "\n";
                }
                return ans;
            }
        });

        setQuestion();
    }


    public void setQuestion(){
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                QuestionParameter[] params = new QuestionParameter[]{new QuestionParameter() {
                    @Override
                    public String getValue() {
                        return "" + (int) (Math.floor(Math.random() * 4) + 1);
                    }
                }, new QuestionParameter() {
                    @Override
                    public String getValue() {
                        return "" + (int) (Math.floor(Math.random() * 4) + 7);
                    }
                }};
                String[] question = bases.get(0).getQuestion(params);
                ((TextView)MainActivity.sharedInstance.findViewById(R.id.questionBox)).setText(question[0]);
                System.out.println(question[1]);
            }
        });
    }



}
