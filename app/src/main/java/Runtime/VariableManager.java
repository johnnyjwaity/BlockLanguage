package Runtime;

import java.util.HashMap;
import java.util.Map;

import Blocks.ParamValue;

public class VariableManager {

    public static VariableManager sharedInstance;

    public VariableManager(){
        sharedInstance = this;
    }

    private Map<String, ParamValue> variables = new HashMap<>();

    public void setVariable(String name, ParamValue value){
        variables.put(name, value);
        System.out.println("Created " + name + " with Value" + value.getRawValue());
    }
    public ParamValue getVariable(String name){
        return variables.get(name);
    }
}
