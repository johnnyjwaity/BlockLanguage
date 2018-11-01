package Gameplay;

import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Blocks.Block;
import Blocks.DeclareVariable;
import Blocks.InlineBlock;
import Blocks.NumBlock;
import Blocks.OperatorBlock;
import Blocks.ParamBlock;
import Blocks.ParameterHolder;
import Blocks.PrintBlock;
import Blocks.StartBlock;
import Blocks.StringBlock;
import Blocks.WhileLoop;

public class GameplayManager {

    public static GameplayManager shared;

    private Map<QuestionBase, QuestionParameter[]> questions = new HashMap<>();
    private String currentAnswer = "";

    public GameplayManager(){
        shared = this;

        QuestionBase b = (new QuestionBase() {
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

            @Override
            public InlineBlock[] getPreset() {
                return new InlineBlock[]{StartBlock.create(), DeclareVariable.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                map.put(NumBlock.create(), inlineBlocks[1].getHolderList().get(0));
                return map;
            }
        });

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

        QuestionBase b2 = (new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print \"<p0>\"";
            }

            @Override
            public String getAnswer(List<String> values) {
                return values.get(0);
            }

            @Override
            public InlineBlock[] getPreset() {
                return new InlineBlock[]{StartBlock.create(), PrintBlock.create(), WhileLoop.create(new InlineBlock[]{StartBlock.create()})};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();


                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
//                map.put(b, inlineBlocks[2].getHolderList().get(0));
                OperatorBlock b2 = OperatorBlock.create();
                map.put(b2, b.getHolderList().get(0));
                map.put(NumBlock.create(), b.getHolderList().get(1));
                map.put(NumBlock.create(), b2.getHolderList().get(0));

                return map;
            }
        });
        QuestionParameter[] params2 = new QuestionParameter[]{new QuestionParameter() {
            @Override
            public String getValue() {
                return "Hello World";
            }
        }};
        questions.put(b2, params2);
        questions.put(b, params);



        setQuestion();
    }


    public void setQuestion(){
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QuestionBase base = (QuestionBase) questions.keySet().toArray()[((int)Math.floor(Math.random() * questions.size()))];
                base = (QuestionBase) questions.keySet().toArray()[(int)(Math.random() * 2)];
                String[] question = base.getQuestion(questions.get(base));
                ((TextView)MainActivity.sharedInstance.findViewById(R.id.questionBox)).setText(question[0]);
                base.setWorkflow();
                currentAnswer = question[1];
            }
        });
    }

    public void checkAnswer(String input) {
        System.out.println("ans: " + currentAnswer);
        if (input.equals(currentAnswer)){
            System.out.println("Correct");
        }else{
            System.out.println("Incorrect");
        }
    }
}
