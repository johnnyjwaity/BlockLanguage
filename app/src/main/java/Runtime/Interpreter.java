package Runtime;

import android.view.View;
import android.widget.TextView;

import com.johnnywaity.blocklanguage.MainActivity;
import com.johnnywaity.blocklanguage.R;

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
        while(currentBlock.getSnappedView() != null){
            currentBlock = currentBlock.getSnappedView();
            currentBlock.execute();
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
