package com.bham.pij.assignments.pontoon;
// Sorina Andreea Ghimpu 2196670

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {

    /////////////////// CLASS VARIABLES /////////////////////

    ArrayList<Card> hand = new ArrayList<>(); //player's hand
    int cardNum; //number of cards in player's hand
    private String name;

    ///////////////////////// END OF CLASS VARIABLES /////////////////////////




    /////////////////////////////// FOR PLAYER'S NAME ///////////////////////////////

    public Player(String name) //constructor for player's name
    {
        this.name = name;
    }
    public String getName() //getter for player's name
    {
        return this.name;
    }

    /////////////////////////////// END OF STORING PLAYER'S NAME ///////////////////////////////




    ////////////////////////// FOR DEALING OR REMOVING CARDS ////////////////////////////

    public void dealToPlayer(Card card) //add a card to player
    {
        hand.add(card);
        cardNum++;
    }

    public void removeCard(Card card) //remove a card from player
    {
        hand.remove(card);
        cardNum--;
    }

    /////////////////////// END OF DEALING OR REMOVING CARDS ////////////////////////////




    /////////////////////// FOR GETTING THE SUM FROM THE CARDS //////////////////////////

    public ArrayList<Integer> getNumericalHandValue() //returns the sum of player's cards; index 0= smallest/normal sum index 1 = highest sum
    {
        ArrayList<Integer> handNumVal = new ArrayList<>();
        int AceNum = 0;
        int i;
        int sum=0;
        for (i = 0; i < hand.size(); i++) //going through the hand arraylist
        {
            if (hand.get(i).getNumericalValue().get(0) == 1) {
                AceNum++;
                sum += 11;
            } //if the card is an ACE, adds 11 instead
            else sum += hand.get(i).getNumericalValue().get(0); //adds numerical value of the card
        }
        handNumVal.add(sum);
        int sumA = sum;
        while (AceNum>0) {
            sumA -= 10;//removes the 11 value of the ACE and adds the 1 value instead
            AceNum--;
            handNumVal.add(sumA);
        }
        Collections.sort(handNumVal);
        return handNumVal;
    } //IT WORKS

    public int getBestNumericalHandValue() //returns the maximum sum of all cards from player's hand
    {
        int AceNum = 0;
        int i;
        int sum=0;
        for (i = 0; i < hand.size(); i++) //going through the hand arraylist
        {
            if (hand.get(i).getNumericalValue().get(0) == 1) {
                AceNum++;
                sum += 11;
            } //if the card is an ACE, adds 11 instead
            else sum += hand.get(i).getNumericalValue().get(0); //adds numerical value of the card
        }
        return sum;
    }

    public int getActualBestNumericalHandValue() //returns the ACTUAL best sum of all cards from player's hand;
    {
        int AceNum = 0;
        int i;
        int sum=0;
        for (i = 0; i < hand.size(); i++) //going through the hand arraylist
        {
            if (hand.get(i).getNumericalValue().get(0) == 1) {
                AceNum++;
                sum += 11;
            } //if the card is an ACE, adds 11 instead
            else sum += hand.get(i).getNumericalValue().get(0); //adds numerical value of the card
        }
        while(sum>21 && AceNum>0)
        {
            sum -= 10;
            AceNum--;
        }
        return sum;
    }

    ////////////////////// END OF GETTING THE SUM FROM THE CARDS /////////////////////////




    /////////////////////// CHECKING PRIORITY OF HAND //////////////////////////

    public int priorityCheck() // Pontoon (4) > Five Card Trick (3) > Totals 21 (2) > Less than 20 (1) > Worthless (0)
    {
        if(getActualBestNumericalHandValue() == 21 && hand.size() == 2) return 4;
        if(getActualBestNumericalHandValue() <=21 && hand.size() == 5) return 3;
        if(getActualBestNumericalHandValue()== 21) return 2;
        if(getActualBestNumericalHandValue() <= 20) return 1;
        if(getActualBestNumericalHandValue() > 21) return 0;
        return 0;
    }

    /////////////////////// END OF PRIORITY CHECKING //////////////////////////




    /////////////////// GETTERS /////////////////////////

    public ArrayList<Card> getCards() //returns player's cards
    {
        return hand;
    }

    public int getHandSize() //returns the number of player's cards
    {
        return hand.size();
    }

    ///////////////// END OF GETTERS ////////////////////////




}
