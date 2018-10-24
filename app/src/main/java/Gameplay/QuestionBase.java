package Gameplay;

import java.util.ArrayList;
import java.util.List;

public abstract class QuestionBase {

    public String[] getQuestion(QuestionParameter[] params) {
        String base = setQuestionBase();
        List<String> values = new ArrayList<>();
        while (base.contains("<p")){

            int index = Integer.parseInt(base.substring(base.indexOf("<p") + 2, base.indexOf(">")));
            String val = params[index].getValue();
            base = base.replaceFirst("<p" + index + ">", val);
            values.add(val);
        }
        return new String[]{base, getAnswer(values)};
    }


    public abstract String setQuestionBase();
    public abstract String getAnswer(List<String> values);
}
