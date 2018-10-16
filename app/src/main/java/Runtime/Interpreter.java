package Runtime;

import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import Blocks.ElseBlock;
import Blocks.IfBlock;
import Blocks.InlineBlock;

public class Interpreter {
    private InlineBlock startBlock;
    public static ScriptEngine scriptEngine;

    public Interpreter(InlineBlock startBlock) {
        this.startBlock = startBlock;
    }

    public void run(){
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
//        try {
//            scriptEngine.eval("print('Hello World');");
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }

        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        rhino.setLanguageVersion(Context.VERSION_1_2);
        Scriptable scope = rhino.initStandardObjects();
        Object res = rhino.evaluateString(scope, "function blah(){return 5;}var a = 1; a = a + blah(); a", "<cmd>", 1, null);
        System.out.println(Context.toString(res));

//        long timeStart = System.currentTimeMillis();
//        VariableManager varManager = new VariableManager();
//        final TextView console = MainActivity.sharedInstance.findViewById(R.id.console);
//        console.setVisibility(View.VISIBLE);
//        console.setText("");
//        console.bringToFront();
//        InlineBlock currentBlock = startBlock;
//        InlineBlock lastBlock = null;
//        while(currentBlock.getSnappedView() != null){
//            lastBlock = currentBlock;
//            currentBlock = currentBlock.getSnappedView();
//            if(currentBlock instanceof ElseBlock){
//                System.out.println("Last " + lastBlock.getClass());
//                if(lastBlock instanceof IfBlock){
//                    System.out.println(((IfBlock) lastBlock).getEvaledValue());
//                    if(!((IfBlock) lastBlock).getEvaledValue()){
//                        currentBlock.execute();
//                        System.out.println("Executed");
//                    }
//                }
//            }else{
//                currentBlock.execute();
//            }
//
//        }
//        timeStart = System.currentTimeMillis() - timeStart;
//        console.setText(console.getText() + "\n\n      Time Ran: " + ((float)timeStart)/1000 + "s");
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        MainActivity.sharedInstance.findViewById(R.id.MenuList).bringToFront();
//                        console.setVisibility(View.INVISIBLE);
//                    }
//                },
//                5000);
    }


}
