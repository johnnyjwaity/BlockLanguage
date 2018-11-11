package Gameplay;

import android.graphics.Color;
import android.graphics.Path;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Handler;

import Blocks.Block;
import Blocks.DeclareVariable;
import Blocks.ElseBlock;
import Blocks.GetVarBlock;
import Blocks.IfBlock;
import Blocks.InlineBlock;
import Blocks.LogicBlock;
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

    private List<Map<QuestionBase, QuestionParameter[]>> questions = new ArrayList<>();
    private String currentAnswer = "";
    private String currentHint = "";
    private List<String> currValues = new ArrayList<>();

    private ProgressBar healthBar;
    int fuel = 100;

    private RelativeLayout background;
    private int[] colors = new int[]{Color.BLACK, Color.rgb(28, 37, 60), Color.rgb(31, 62, 90), Color.rgb(20, 80, 81), Color.rgb(40, 28, 60), Color.rgb(60, 28, 49)};
    private int currentColor = 0;
    private float currentThreshold = 0;


    private int currentLevel = 1;

    public GameplayManager(){
        shared = this;
        MainActivity.sharedInstance.populateMenu(currentLevel);
        populateQuestions();
        setQuestion();

        healthBar = MainActivity.sharedInstance.findViewById(R.id.progressBar);
        healthBar.setProgress(fuel);

        background = MainActivity.sharedInstance.findViewById(R.id.GameView);

        decreaseFuel();
        changeBackground();

    }

    private void decreaseFuel() {

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        fuel -= 1;
                        healthBar.setProgress(fuel);
                        decreaseFuel();
                    }
                },
        50);
    }

    private void changeBackground(){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentThreshold > 1) {
                    currentThreshold = 0;
                    currentColor += 1;
                    if(currentColor >= colors.length){
                        currentColor = 0;
                    }
                }
                background.setBackgroundColor(interpolateColors(colors[currentColor], colors[(currentColor + 1 >= colors.length) ? 0 : currentColor + 1], currentThreshold));
                currentThreshold += 0.05;
                changeBackground();
            }
        }, 250);
    }
    private static int interpolateColors(int color1, int color2, float threshold){
        Color c1 = Color.valueOf(color1);
        Color c2 = Color.valueOf(color2);
        return Color.rgb(c1.red() + ((c2.red() - c1.red()) * threshold), c1.green() + ((c2.green() - c1.green()) * threshold), c1.blue() + ((c2.blue() - c1.blue()) * threshold));

    }

    public void setQuestion(){
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QuestionBase base = (QuestionBase) questions.get(currentLevel - 1).keySet().toArray()[(int)(Math.random() * questions.get(currentLevel - 1).size())];
                String[] question = base.getQuestion(questions.get(currentLevel - 1).get(base));
                currentAnswer = question[1];
                currentHint = base.getHint();
                ((TextView)MainActivity.sharedInstance.findViewById(R.id.questionBox)).setText(question[0]);
                base.setWorkflow();
            }
        });
    }

    public void checkAnswer(String input) {
        String correctAnswer = "";
        String answer = "";
        for(char c : input.toCharArray()){
            if(((int) c) >= 30 && ((int) c) <= 122){
                answer += c;
            }
        }
        for(char c : currentAnswer.toCharArray()){
            if(((int) c) >= 30 && ((int) c) <= 122){
                correctAnswer += c;
            }
        }
        if (answer.equalsIgnoreCase(correctAnswer)){
            System.out.println("Correct");
        }else{
            System.out.println("Incorrect");
        }
    }


    public String getHint(){
        return currentHint;
    }







    private void populateQuestions(){

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

            @Override
            public String getHint() {
                return "Hint 1";
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
                return new InlineBlock[]{StartBlock.create(), PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                map.put(StringBlock.create(), inlineBlocks[1].getHolderList().get(0));
                return map;
            }
            @Override
            public String getHint() {
                return "Click the white space in the String block to add words";
            }
        });
        QuestionParameter[] params2 = new QuestionParameter[]{new QuestionParameter() {
            @Override
            public String getValue() {
                return "Hello World";
            }
        }};

        QuestionBase printRandNumber = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print <p0>";
            }

            @Override
            public String getAnswer(List<String> values) {
                return values.get(0);
            }

            @Override
            public InlineBlock[] getPreset() {
                return new InlineBlock[]{StartBlock.create(), PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                map.put(NumBlock.create(), inlineBlocks[1].getHolderList().get(0));
                return map;
            }
            @Override
            public String getHint() {
                return "Click the white space in the Num block to add a number";
            }
        };
        QuestionParameter randNumberParams = new QuestionParameter() {
            @Override
            public String getValue() {
                return "" + ((int)(Math.random() * 100) + 1);
            }
        };

        QuestionBase printNumberAndString = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print <p0> and then print Hello World";
            }

            @Override
            public String getAnswer(List<String> values) {
                return values.get(0)+"\n Hello World";
            }

            @Override
            public InlineBlock[] getPreset() {
                return new InlineBlock[]{StartBlock.create(), PrintBlock.create(), PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                map.put(NumBlock.create(), inlineBlocks[1].getHolderList().get(0));
                map.put(StringBlock.create(), inlineBlocks[2].getHolderList().get(0));
                return map;
            }
            @Override
            public String getHint() {
                return "Click the white space in the Num block to add a number. Click the white space in the String block to add words";
            }
        };

        QuestionBase printHelloWorldSelf = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print Hello World";
            }

            @Override
            public String getAnswer(List<String> values) {
                return "Hello World";
            }

            @Override
            public InlineBlock[] getPreset() {
                return new InlineBlock[]{};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                return new HashMap<>();
            }
            @Override
            public String getHint() {
                return "You Need a Start Block, Print Block, and String Block";
            }
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
        questions.get(0).put(b2, params2);
        questions.get(0).put(printRandNumber, new QuestionParameter[]{randNumberParams});
        questions.get(0).put(printNumberAndString, new QuestionParameter[]{randNumberParams});
        questions.get(0).put(printHelloWorldSelf, new QuestionParameter[]{});

        QuestionBase varTimesNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                currValues = new ArrayList<>();
                currValues.add(""+((int)(Math.random() * 10) + 1));
                return "Print x times <p0>";
            }

            @Override
            public String getAnswer(List<String> values) {
                return ""+Integer.parseInt(values.get(0))*Integer.parseInt(currValues.get(0));
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                return new InlineBlock[]{StartBlock.create(), b, PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                map.put(GetVarBlock.create(), b.getHolderList().get(0));

                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };
        QuestionParameter randNumberParams10 = new QuestionParameter() {
            @Override
            public String getValue() {
                return "" + ((int)(Math.random() * 10) + 1);
            }
        };

        QuestionBase xMinusY = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                currValues = new ArrayList<>();
                currValues.add(""+((int)(Math.random() * 5) + 6));
                currValues.add(""+((int)(Math.random() * 5) + 1));
                return "Print x minus y";
            }

            @Override
            public String getAnswer(List<String> values) {
                return Integer.parseInt(currValues.get(0))-Integer.parseInt(currValues.get(1))+"";
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                DeclareVariable b2 = DeclareVariable.create();
                b2.changeVariableName("y");
                return new InlineBlock[]{StartBlock.create(), b, b2, PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB, inlineBlocks[1].getHolderList().get(0));

                NumBlock numB2 = NumBlock.create();
                numB2.setValue(Integer.parseInt(currValues.get(1)));
                map.put(numB2, inlineBlocks[2].getHolderList().get(0));

                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[3].getHolderList().get(0));
//                map.put(GetVarBlock.create(), b.getHolderList().get(0));
//                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };

        QuestionBase twoToPowerOfX = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                currValues = new ArrayList<>();
                currValues.add(""+((int)(Math.random() * 7) + 1));
                return "Print 2 to the power of x";
            }

            @Override
            public String getAnswer(List<String> values) {
                return ""+(Math.pow(2, Integer.parseInt(currValues.get(0))));
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                return new InlineBlock[]{StartBlock.create(), b, PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                map.put(NumBlock.create(), b.getHolderList().get(0));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };

        QuestionBase xPlusyPlusNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                currValues = new ArrayList<>();
                currValues.add(""+((int)(Math.random() * 10) + 1));
                currValues.add(""+((int)(Math.random() * 10) + 1));
                return "Print x + y + <p0>";
            }

            @Override
            public String getAnswer(List<String> values) {
                return Integer.parseInt(currValues.get(0))+Integer.parseInt(currValues.get(1))+Integer.parseInt(values.get(0))+"";
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                DeclareVariable b2 = DeclareVariable.create();
                b2.changeVariableName("y");
                return new InlineBlock[]{StartBlock.create(), b, b2, PrintBlock.create()};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB, inlineBlocks[1].getHolderList().get(0));

                NumBlock numB2 = NumBlock.create();
                numB2.setValue(Integer.parseInt(currValues.get(1)));
                map.put(numB2, inlineBlocks[2].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[3].getHolderList().get(0));
                map.put(OperatorBlock.create(), b.getHolderList().get(1));
//                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
        questions.get(1).put(varTimesNum, new QuestionParameter[]{randNumberParams10});
        questions.get(1).put(xMinusY, new QuestionParameter[]{});
        questions.get(1).put(twoToPowerOfX, new QuestionParameter[]{});
        questions.get(1).put(xPlusyPlusNum, new QuestionParameter[]{randNumberParams10});

        QuestionBase printTrueFalseIfXEqualsNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print True if x equals <p0>. Else, print False";
            }

            @Override
            public String getAnswer(List<String> values) {
                currValues = values;
                return "3".equals(values.get(0))+"";
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                return new InlineBlock[]{StartBlock.create(), b, IfBlock.create(new InlineBlock[]{PrintBlock.create()}), ElseBlock.create(new InlineBlock[]{PrintBlock.create()})};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(3);
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                LogicBlock b = LogicBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                GetVarBlock varB = GetVarBlock.create();
                varB.changeVarName("x");
                map.put(varB, b.getHolderList().get(0));
                NumBlock numB2 = NumBlock.create();
                numB2.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB2, b.getHolderList().get(1));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };

        QuestionParameter randNumberParams3or4 = new QuestionParameter() {
            @Override
            public String getValue() {
                return "" + ((int)(Math.random() * 2) + 3);
            }
        };

        QuestionBase printIfXTimesNumGreaterThanNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                currValues = new ArrayList<>();
                currValues.add(""+((int)(Math.random() * 3) + 2));
                return "Print True if x * <p0> is greater than <p1>. Else, print False";
            }

            @Override
            public String getAnswer(List<String> values) {
                currValues.add(values.get(0));
                currValues.add(values.get(1));
                return (Integer.parseInt(currValues.get(0))*Integer.parseInt(values.get(0))>Integer.parseInt(values.get(1)))+"";
            }

            @Override
            public InlineBlock[] getPreset() {
                DeclareVariable b = DeclareVariable.create();
                b.changeVariableName("x");
                return new InlineBlock[]{StartBlock.create(), b, IfBlock.create(new InlineBlock[]{PrintBlock.create()}), ElseBlock.create(new InlineBlock[]{PrintBlock.create()})};
            }

            @Override
            public Map<ParamBlock, ParameterHolder> getParamPreset(InlineBlock[] inlineBlocks) {
                Map<ParamBlock, ParameterHolder> map = new HashMap<>();
                NumBlock numB = NumBlock.create();
                numB.setValue(Integer.parseInt(currValues.get(0)));
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                LogicBlock b = LogicBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                OperatorBlock opB = OperatorBlock.create();
                map.put(opB, b.getHolderList().get(0));
                GetVarBlock varB = GetVarBlock.create();
                varB.changeVarName("x");
                map.put(varB, opB.getHolderList().get(0));
                NumBlock numB2 = NumBlock.create();
                numB2.setValue(Integer.parseInt(currValues.get(2)));
                map.put(numB2, b.getHolderList().get(1));
                NumBlock numB3 = NumBlock.create();
                numB3.setValue(Integer.parseInt(currValues.get(1)));
                map.put(numB3, opB.getHolderList().get(1));
                return map;
            }
            @Override
            public String getHint() {
                return "";
            }
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
//        questions.get(2).put(printTrueFalseIfXEqualsNum, new QuestionParameter[]{randNumberParams3or4});
        questions.get(2).put(printIfXTimesNumGreaterThanNum, new QuestionParameter[]{randNumberParams10, randNumberParams10});


    }
}
