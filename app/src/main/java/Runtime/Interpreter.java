package Runtime;

import Blocks.InlineBlock;

public class Interpreter {
    private InlineBlock startBlock;

    public Interpreter(InlineBlock startBlock) {
        this.startBlock = startBlock;
    }

    public void run(){
        VariableManager varManager = new VariableManager();

        InlineBlock currentBlock = startBlock;
        while(currentBlock.getSnappedView() != null){
            currentBlock = currentBlock.getSnappedView();
            currentBlock.execute();
        }
    }


}
