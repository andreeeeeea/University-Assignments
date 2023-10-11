package com.bham.pij.assignments.calculator;
// Sorina Andreea Ghimpu 2196670


import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    String expression = ""; //the input expression
    private float currentValue; //where the current value of the result is
    private float memval; //where the value will be stored if the user types in m
    int index; //used in the getHistoryValue method
    ArrayList<Float> History = new ArrayList<>(); //where all of the results will be automatically saved



    /////////////////////////////
    // BEGINNING OF EXERCISE 1 //
    /////////////////////////////


    public Calculator() {

    }
    //default constructor


    public float getResult(float a, float b, String ope)
    {
        if(ope.equals("+"))return a+b;
        if(ope.equals("-"))return a-b;
        if(ope.equals("*"))return a*b;
        if(ope.equals("/"))return a/b;
        return 0;
    }
    //returns result between a ope b (ope = operator in String form, a and b = operands in float form)


    public int getPrecedence(String op)
    {
        if(op.equals("+") || op.equals("-"))return -1;
        if(op.equals("*")) return 1;
        if(op.equals("/"))return 1;
        if(op.equals("("))return 2;
        if(op.equals(")"))return 3;
        return -1;
    }
    //get priority of op (op = operator)
    // )         ^   highest priority
    // (         |
    // * and /   |
    // + and -   |    smallest priority


    public int isOperator(String x)
    {
        if(x.equals("+") || x.equals("-") || x.equals("/") || x.equals("*"))return 1;
        else return 0;
    }
    //check if x is +, -, / or *


    public int checkFormat(String expression)
    {
        if (expression.matches("-?\\d+(?:\\.\\d+)?(\\s[*+/-]\\s-?\\d+(?:\\.\\d+)?)+") || expression.matches("-?[1-9]\\d*+(?:\\.\\d+)?(\\s[/]\\s-?[1-9]\\d*+(?:\\.\\d+)?)+"))
        {
            String[] e = expression.split(" ");
            int i;
            if(e[e.length - 1].charAt(0) == '0' && e[e.length - 2].charAt(0) == '/')return 0;
            for(i=0; i<e.length-1; i++)
            {
                if((e[i].charAt(0) == '0' && e[i].length() < 2 && e[i+1].equals("/") ) ||
                        (e[i].equals("/") && e[i+1].charAt(0) == '0' && e[i].length() < 2))return 0;
            }
            return 1;
        }
        if (expression.matches("[*+-]\\s-?\\d+(?:\\.\\d+)?") || expression.matches("[/]\\s-?[1-9]\\d*+(?:\\.\\d+)?")) return 2; //for the memory calculations
        if(expression.matches("[(]-?\\d+(?:\\.\\d+)?\\s[*+-]\\s-?\\d+(?:\\.\\d+)?[)]\\s[*/+-]\\s[(]-?\\d+(?:\\.\\d+)?\\s[*+-]\\s-?\\d+(?:\\.\\d+)?[)]") ||
                expression.matches("[(]-?[1-9]\\d*+(?:\\.\\d+)?\\s[/]\\s-?[1-9]\\d*+(?:\\.\\d+)?[)]\\s[*/+-]\\s[(]-?[1-9]\\d*+(?:\\.\\d+)?\\s[/]\\s-?[1-9]\\d*+(?:\\.\\d+)?[)]")) return 3; //for brackets
        return 0;
    }
    //check the formatting of the input expression


    public ArrayList<String> toRPN(String[] expression)
    {
        Stack<String> ops = new Stack<>();
        ArrayList<String> postfix= new ArrayList<>();
        int i;
        for(i=0; i<expression.length; i++)
        {
            if(isOperator(expression[i]) == 0 && expression[i].equals("(") == false && expression[i].equals(")") == false)
                postfix.add(expression[i]);
            else if(expression[i].equals("(") == true)
                ops.push(expression[i]);
            else if(expression[i].equals(")") == true)
            {
                while(ops.isEmpty() == false && ops.peek().equals("(") == false)
                {
                    postfix.add(ops.peek());
                    ops.pop();
                }
                ops.pop();
            }
            else if(isOperator(expression[i]) == 1)
            {
                while(ops.isEmpty() == false && ops.peek().equals("(") == false && getPrecedence(ops.peek()) >= getPrecedence(expression[i]))
                {
                    postfix.add(ops.peek());
                    ops.pop();
                }
                ops.push(expression[i]);
            }
        } //end of for
        while(ops.isEmpty() == false)
        {
            postfix.add(ops.peek());
            ops.pop();
        }
        return postfix;
    }
    //converts a String[] array of the form 1+2+3+4 (has used the split method) to a RPN String


    public float evaluate(String expression)
    {
        if (checkFormat(expression) == 1) {
            Stack<Float> Numbers = new Stack<>(); //stack where the numbers will be put; if we encounter an operation, we use getResult, pop the first two numbers and insert the result
            String[] exp = expression.split(" "); //split the expression e.g. from 1 + 2 to [1, +, 2]
            ArrayList<String> RPN = new ArrayList<>();
            RPN = toRPN(exp); //the RPN is stored here
            int i; //will be using to go through the RPN
            for (i = 0; i < RPN.size(); i++) //going through the RPN
            {
                if (isOperator(RPN.get(i)) == 0) //if RPN.get(i) is a number
                {
                    Numbers.push(Float.parseFloat(RPN.get(i))); //convert the string to a float and push it into the stack
                }
                if (isOperator(RPN.get(i)) == 1)//if RPN.get(i) is an operator
                {
                    float a = Numbers.pop(); //top of the stack
                    float b = Numbers.pop(); //under the top of the stack
                    float t;
                    t = a;
                    a = b;
                    b = t; //switch a and b
                    float r = getResult(a, b, RPN.get(i));
                    Numbers.push(r);
                }
            }
            currentValue = Numbers.pop();
        }

        else if(checkFormat(expression) == 2) // for Exercise 2 evaluates expressions of the form + 2, * 3, / 4, and so on
        {
            String[] exp = expression.split(" ");
            currentValue = getResult(memval, Float.parseFloat(exp[1]), exp[0]);
        }

        else if(checkFormat(expression) == 3) //it's a bracket expression, for Exercise 3
        {
            expression = expression.replaceAll("\\(", "\\( ");
            expression = expression.replaceAll("\\)", " \\)");// from (1 + 2) to ( 1 + 2 )
            Stack<Float> Numbers = new Stack<>(); //stack where the numbers will be put; if we encounter an operation, we use getResult, pop the first two numbers and insert the result
            String[] exp = expression.split(" "); //split the expression e.g. from 1 + 2 to [1, +, 2]
            ArrayList<String> RPN = new ArrayList<>();
            RPN = toRPN(exp); //the RPN is stored here
            int i; //will be using to go through the RPN
            for (i = 0; i < RPN.size(); i++) //going through the RPN
            {
                if (isOperator(RPN.get(i)) == 0) //if RPN.get(i) is a number
                {
                    Numbers.push(Float.parseFloat(RPN.get(i))); //convert the string to a float and push it into the stack
                }
                if (isOperator(RPN.get(i)) == 1)//if RPN.get(i) is an operator
                {
                    float a = Numbers.pop(); //top of the stack
                    float b = Numbers.pop(); //under the top of the stack
                    float t;
                    t = a;
                    a = b;
                    b = t; //switch a and b
                    float r = getResult(a, b, RPN.get(i));
                    Numbers.push(r);
                }
            }
            currentValue = Numbers.pop();
        }

        else if(checkFormat(expression) == 0)
        {
            currentValue = Float.MIN_VALUE;
        }
        if(currentValue!=Float.MIN_VALUE && currentValue != 0.0)History.add(currentValue);
         return currentValue;
    }
    //evaluates the RPN


    public void setCurrentValue(float currentValue)
    {
        this.currentValue = currentValue;
    }
    //sets current value


    public float getCurrentValue()
    {
        if(currentValue!=Float.MIN_VALUE) return currentValue;
        else return 0;
    }
    //if invalid format, returns 0, else returns the result


    ///////////////////////
    // END OF EXERCISE 1 //
    ///////////////////////




    //////////////////////////////
    // BEGINNING OF EXERCISE 2 ///
    //////////////////////////////


    public void addToHistory(){
        if(currentValue==Float.MIN_VALUE || currentValue == 0.0)History.add(Float.parseFloat("0.0"));
        else History.add(currentValue);
    }
    //adds the latest result to history, regardless of what the user has written


    public float getHistoryValue(int index)
    {
        return History.get(index);
    }
    //gets the result from the history list at the specified index

    public void showHistory()
    {
        StringBuilder hisBuild = new StringBuilder();
        System.out.println("All of the results you've gotten so far are: ");
        int i;
        for(i=0; i<History.size(); i++)
        {
            hisBuild.append(History.get(i));
            hisBuild.append(" ");
        }
        System.out.println(hisBuild.toString());
    }
    //if the user types in h, shows all of the results they've gotten so far


    public void setMemoryValue(float memval)
    {
        this.memval = memval;
    }

    public float getMemoryValue()
    {
        return memval;
    }

    public void clearMemory()
    {
        memval = Float.parseFloat("0.0");
    }


    //////////////////////
    // END OF EXERCISE 2//
    //////////////////////




    ///////////////////////////////////////////////////////////////
    // EXERCISE 3 AND 4 HAVE BEEN INPUT IN THE EVALUATE() METHOD //
    ///////////////////////////////////////////////////////////////


}
