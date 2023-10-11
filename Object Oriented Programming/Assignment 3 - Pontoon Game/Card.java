package com.bham.pij.assignments.pontoon;
// Sorina Andreea Ghimpu 2196670

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Card {

    /////////////////// CLASS VARIABLES /////////////////////

    private Suit mySuit;
    private Value myValue;

    ///////////////////////// END OF CLASS VARIABLES /////////////////////////




    ///////////////////////// SUIT AND VALUE ENUMS /////////////////////////

    public static enum Suit
    {
        CLUBS,
        HEARTS,
        DIAMONDS,
        SPADES
    }

    public static enum Value
    {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING
    }

    ///////////////////////// END OF SUIT AND VALUE ENUMS /////////////////////////




    ///////////////////////// GETTERS AND SETTERS /////////////////////////

    public Suit getSuit()
    {
        return mySuit;
    }

    public Value getValue()
    {
        return myValue;
    }

    public void setSuitAndValue(Suit newSuit, Value newValue)
    {
        this.mySuit = newSuit;
        this.myValue = newValue;
    }

    ///////////////////////// END OF GETTERS AND SETTERS /////////////////////////




    ///////////////////////// CARD CONSTRUCTOR /////////////////////////

    public Card(Suit s, Value v)
    {
        this.mySuit = s;
        this.myValue = v;
    }

    ///////////////////////// END OF CARD CONSTRUCTOR /////////////////////////



    ///////////////////////// GETTING A CARD'S NUMERICAL VALUE /////////////////////////

    public ArrayList<Integer> getNumericalValue() //gets numerical value for a card, for example: TWO => 2
    {
        ArrayList<Integer> numVal = new ArrayList<>();
        if(getValue().equals(Value.ACE)) {numVal.add(1); numVal.add(11); return numVal; }
        if(getValue().equals(Value.TWO)){numVal.add(2); return numVal; }
        if(getValue().equals(Value.THREE)){numVal.add(3); return numVal; }
        if(getValue().equals(Value.FOUR)){numVal.add(4); return numVal; }
        if(getValue().equals(Value.FIVE)){numVal.add(5); return numVal; }
        if(getValue().equals(Value.SIX)){numVal.add(6); return numVal; }
        if(getValue().equals(Value.SEVEN)){numVal.add(7); return numVal; }
        if(getValue().equals(Value.EIGHT)){numVal.add(8); return numVal; }
        if(getValue().equals(Value.NINE)){numVal.add(9); return numVal; }
        if(getValue().equals(Value.TEN)){numVal.add(10); return numVal; }
        if(getValue().equals(Value.JACK)){numVal.add(10); return numVal; }
        if(getValue().equals(Value.QUEEN)){numVal.add(10); return numVal; }
        if(getValue().equals(Value.KING)){numVal.add(10); return numVal; }
        return numVal;
    }

    ///////////////////////// GETTING A CARD'S NUMERICAL VALUE /////////////////////////




    ///////////////////////// METHOD USED FOR TESTING /////////////////////////

    public void printing() //prints out a card; for example: TWO of HEARTS
    {
        System.out.println(getValue() + " of " + getSuit());
    }

    ///////////////////////// END OF METHOD USED FOR TESTING /////////////////////////


}
