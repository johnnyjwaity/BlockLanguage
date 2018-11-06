package Gameplay;

import android.graphics.Path;
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

    private int currentLevel = 3;

    public GameplayManager(){
        shared = this;
        MainActivity.sharedInstance.populateMenu(currentLevel);
        populateQuestions();
        setQuestion();
    }


    public void setQuestion(){
        MainActivity.sharedInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QuestionBase base = (QuestionBase) questions.get(currentLevel - 1).keySet().toArray()[(int)(Math.random() * questions.get(currentLevel - 1).size())];
                String[] question = base.getQuestion(questions.get(currentLevel - 1).get(base));
                ((TextView)MainActivity.sharedInstance.findViewById(R.id.questionBox)).setText(question[0]);
                base.setWorkflow();
                currentAnswer = question[1];
            }
        });
    }

    public void checkAnswer(String input) {
        System.out.println("ans: " + currentAnswer);
        System.out.println("ans: "+ input.replace(" ", ""));
        if (input.replace(" ", "").equalsIgnoreCase(currentAnswer.replace(" ", ""))){
            System.out.println("Correct");
        }else{
            System.out.println("Incorrect");
        }
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
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
        questions.get(0).put(b2, params2);
        questions.get(0).put(printRandNumber, new QuestionParameter[]{randNumberParams});
        questions.get(0).put(printNumberAndString, new QuestionParameter[]{randNumberParams});
        questions.get(0).put(printHelloWorldSelf, new QuestionParameter[]{});

        QuestionBase varTimesNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print x times <p0>";
            }

            @Override
            public String getAnswer(List<String> values) {
                return ""+Integer.parseInt(values.get(0))*5;
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
                numB.setValue(5);
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                map.put(GetVarBlock.create(), b.getHolderList().get(0));

                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
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
                return "Print x minus y";
            }

            @Override
            public String getAnswer(List<String> values) {
                return "7";
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
                numB.setValue(10);
                map.put(numB, inlineBlocks[1].getHolderList().get(0));

                NumBlock numB2 = NumBlock.create();
                numB2.setValue(3);
                map.put(numB2, inlineBlocks[2].getHolderList().get(0));

                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[3].getHolderList().get(0));
//                map.put(GetVarBlock.create(), b.getHolderList().get(0));
//                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
        };

        QuestionBase twoToPowerOfX = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print 2 to the power of x";
            }

            @Override
            public String getAnswer(List<String> values) {
                return "16";
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
                numB.setValue(4);
                map.put(numB, inlineBlocks[1].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                map.put(NumBlock.create(), b.getHolderList().get(0));
                return map;
            }
        };

        QuestionBase xPlusyPlusNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print x + y + <p0>";
            }

            @Override
            public String getAnswer(List<String> values) {
                return (7+values.get(0))+"";
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
                numB.setValue(-2);
                map.put(numB, inlineBlocks[1].getHolderList().get(0));

                NumBlock numB2 = NumBlock.create();
                numB2.setValue(9);
                map.put(numB2, inlineBlocks[2].getHolderList().get(0));
                OperatorBlock b = OperatorBlock.create();
                map.put(b, inlineBlocks[3].getHolderList().get(0));
                map.put(OperatorBlock.create(), b.getHolderList().get(1));
//                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
//        questions.get(1).put(varTimesNum, new QuestionParameter[]{randNumberParams10});
//        questions.get(1).put(xMinusY, new QuestionParameter[]{});
        questions.get(1).put(twoToPowerOfX, new QuestionParameter[]{});
        questions.get(1).put(xPlusyPlusNum, new QuestionParameter[]{randNumberParams10});

        QuestionBase printTrueFalseIfXEqualsNum = new QuestionBase() {
            @Override
            public String setQuestionBase() {
                return "Print True if x equals <p0>. Else, print False";
            }

            @Override
            public String getAnswer(List<String> values) {
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
                map.put(NumBlock.create(), inlineBlocks[1].getHolderList().get(0));
                LogicBlock b = LogicBlock.create();
                map.put(b, inlineBlocks[2].getHolderList().get(0));
                map.put(GetVarBlock.create(), b.getHolderList().get(0));
                map.put(NumBlock.create(), b.getHolderList().get(1));
//                map.put(NumBlock.create(), b.getHolderList().get(1));
                return map;
            }
        };

        questions.add(new HashMap<QuestionBase, QuestionParameter[]>());
        questions.get(2).put(printTrueFalseIfXEqualsNum, new QuestionParameter[]{randNumberParams10});


    }
}
