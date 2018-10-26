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
import Gameplay.GameplayManager;

public class Interpreter {
    private InlineBlock startBlock;
    public static ScriptEngine scriptEngine;

    public Interpreter(InlineBlock startBlock) {
        this.startBlock = startBlock;
    }

    public void run(){
        long timeStart = System.currentTimeMillis();
        final TextView console = MainActivity.sharedInstance.findViewById(R.id.console);
        console.setVisibility(View.VISIBLE);
        console.setText("");
        console.bringToFront();
        InlineBlock currentBlock = startBlock;
        String executeStr = "var PRINT_STR = ''; function print(a){PRINT_STR += a + '\\n';}";
        while(currentBlock.getSnappedView() != null){
            currentBlock = currentBlock.getSnappedView();
            executeStr += currentBlock.getJSValue();

        }
        executeStr += "PRINT_STR";
        System.out.println(executeStr);
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        rhino.setLanguageVersion(Context.VERSION_1_2);
        Scriptable scope = rhino.initStandardObjects();
        Object res = rhino.evaluateString(scope, executeStr, "<cmd>", 1, null);
        System.out.println("output: " + Context.toString(res)+"\n output done");
        String result = Context.toString(res);
        GameplayManager.shared.checkAnswer(result);
        timeStart = System.currentTimeMillis() - timeStart;
        console.setText(result + "\n      Time Ran: " + ((float)timeStart)/1000 + "s");

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        MainActivity.sharedInstance.findViewById(R.id.MenuList).bringToFront();
                        console.setVisibility(View.INVISIBLE);
                    }
                },
                5000);
    }


}
