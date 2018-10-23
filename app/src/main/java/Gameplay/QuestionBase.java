package Gameplay;

public abstract class QuestionBase {

    public String getQuestion(QuestionParameter[] params) {
        String base = setQuestionBase();
        while (base.contains("<p")){

            int index = Integer.parseInt(base.substring(base.indexOf("<p") + 2, base.indexOf(">")));
            base = base.replaceFirst("<p" + index + ">", params[index].getValue());
        }
        return base;
    }


    public abstract String setQuestionBase();
}
