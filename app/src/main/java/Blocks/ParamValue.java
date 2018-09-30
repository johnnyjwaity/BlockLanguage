package Blocks;

public class ParamValue {
    private DataType dataType;
    private float numValue;
    private String stringValue;

    public ParamValue(DataType dataType) {
        this.dataType = dataType;
    }

    public float getNumValue() {
        return numValue;
    }

    public void setNumValue(float numValue) {
        this.numValue = numValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}

