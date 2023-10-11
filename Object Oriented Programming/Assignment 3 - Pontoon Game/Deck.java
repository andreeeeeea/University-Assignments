package com.bham.pij.assignments.pontoon;
// Sorina Andreea Ghimpu 2196670

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    /////////////////// CLASS VARIABLES /////////////////////

    ArrayList<Card> deck;


    ///////////////////////// END OF CLASS VARIABLES /////////////////////////




    /////////////////// DECK CONSTRUCTOR/RESETTING/SHUFFLING /////////////////////

    public Deck() // deck constructor
    {
        this.deck = new ArrayList<Card>();
        reset();
    }

    public void reset() //resets the deck; first, it clears the deck then re-constructs it
    {
        this.deck.clear();
        for(Card.Suit s : Card.Suit.values())
            for(Card.Value v : Card.Value.values())
            {
                Card c = new Card(s, v);
                this.deck.add(c);
            }
    }

    public void shuffle() //shuffles the deck
    {
        Collections.shuffle(this.deck);
    }

    /////////////////// END OF DECK CONSTRUCTOR/RESETTING/SHUFFLING /////////////////////




    /////////////////// GETTING A CARD AND DEALING A RANDOM CARD /////////////////////

    public Card getCard(int i) //gets card from index i
    {
        return this.deck.get(i);
    }

    public int getRandomNumber() //returns a random number from 1 to 52
    {
        Random a = new Random();
        return a.nextInt(this.deck.size()-1) + 1;
    }

    public Card dealRandomCard() //deals a random card to player
    {
        int i = getRandomNumber();
        Card randomCard = getCard(i);
        this.deck.remove(randomCard);
        return randomCard;
    }

    public int size() //returns the deck's size
    {
        return this.deck.size();
    }

    /////////////////// END OF GETTING A CARD AND DEALING A RANDOM CARD /////////////////////
}
