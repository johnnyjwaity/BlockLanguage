package Blocks;

public class ParamValue {
    private DataType dataType;
    private float numValue;
    private String stringValue;
    private Boolean boolValue;

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

    public Boolean getBool() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public String getRawValue(){
        switch(dataType){
            case Number:
                if ((float) Math.floor((double)numValue) == numValue) {
                    return ""+(int) numValue;
                }
                return "" + numValue;
            case String:
                return stringValue;
            case Boolean:
                return ""+boolValue;
        }
        return null;
    }

    public DataType getDataType() {
        return dataType;
    }
}

