package Runtime;

import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

import Blocks.ElseBlock;
import Blocks.IfBlock;
import Blocks.InlineBlock;

public class Interpreter {
    private InlineBlock startBlock;

    public Interpreter(InlineBlock startBlock) {
        this.startBlock = startBlock;
    }

    public void run(){
        long timeStart = System.currentTimeMillis();
        VariableManager varManager = new VariableManager();
        final TextView console = MainActivity.sharedInstance.findViewById(R.id.console);
        console.setVisibility(View.VISIBLE);
        console.setText("");
        console.bringToFront();
        InlineBlock currentBlock = startBlock;
        InlineBlock lastBlock = null;
        while(currentBlock.getSnappedView() != null){
            lastBlock = currentBlock;
            currentBlock = currentBlock.getSnappedView();
            if(currentBlock instanceof ElseBlock){
                System.out.println("Last " + lastBlock.getClass());
                if(lastBlock instanceof IfBlock){
                    System.out.println(((IfBlock) lastBlock).getEvaledValue());
                    if(!((IfBlock) lastBlock).getEvaledValue()){
                        currentBlock.execute();
                        System.out.println("Executed");
                    }
                }
            }else{
                currentBlock.execute();
            }

        }
        timeStart = System.currentTimeMillis() - timeStart;
        console.setText(console.getText() + "\n\n      Time Ran: " + ((float)timeStart)/1000 + "s");

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
