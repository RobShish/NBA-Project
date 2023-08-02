package controller;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator{
    private LinkedList<String> errors;
    private final String namePattern = "^[A-Z][a-zA-Z ]+$";
    private final String numberPattern = "^\\d+$";
    private final String decimalPattern = "^-?\\d+(\\.\\d+)?$";

    public Validator(){
        this.errors = new LinkedList<>();
    }

    public boolean validate(String pattern, String input) {
        Pattern regEx = Pattern.compile(pattern);
        Matcher match = regEx.matcher(input);
        return match.matches();
    }

    public boolean isEmpty(String input) {
        return input.isEmpty();
    }

    public void generateErrors(String name){
        errors.clear();
        if(!validate(namePattern,name))
            addError("Incorrect name pattern!\n");
    }
    public void generateErrors(String name, String credit, String age, String no){
        errors.clear();
        if(!validate(namePattern,name))
            addError("Incorrect name pattern!\n");
        if(!validate(decimalPattern, credit))
            addError("Incorrect credit pattern!\n");
        if(!validate(numberPattern, age))
            addError("Incorrect age pattern!\n");
        if(!validate(numberPattern, no))
            addError("Incorrect number pattern!\n");
    }
    public boolean isValid(String name){
        return validate(namePattern,name);
    }

    public boolean isValid(String name, String credit, String age, String no){
        return validate(namePattern,name) &&
                validate(decimalPattern, credit) &&
                validate(numberPattern, age) &&
                validate(numberPattern, no);
    }

    public void addError(String msg){
        this.errors.add(msg);
    }

    public LinkedList<String> errors(){
        return this.errors;
    }
}
