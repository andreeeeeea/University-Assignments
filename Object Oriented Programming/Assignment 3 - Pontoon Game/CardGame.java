package com.bham.pij.assignments.pontoon;
// Sorina Andreea Ghimpu 2196670

import java.util.ArrayList;

abstract public class CardGame {

    ///////////////////////// CLASS VARIABLES /////////////////////////

    private Deck deck; //deck used in game
    private int NumPlayers; //number of players
    private ArrayList<Player> players; //arraylist of players
    private Player pl;

    ///////////////////////// END OF CLASS VARIABLES /////////////////////////




    ///////////////////////// CARDGAME CONSTRUCTOR /////////////////////////

    public CardGame(int nplayers) //constructor of the game
    {
        this.deck = new Deck();
        this.NumPlayers = nplayers;
        int i;
        players = new ArrayList<Player>(nplayers);
        for(i=0; i<nplayers; i++)
        {
            pl = new Player(Integer.toString(i+1));
            players.add(pl);

        }
    }

    ///////////////////////// END OF CARDGAME CONSTRUCTOR /////////////////////////




    ///////////////////////// ABSTRACT METHODS  /////////////////////////

    public abstract void dealInitialCards(); //abstract method used for dealing 2 cards to each player

    public abstract int compareHands(Player hand1, Player hand2);

    ///////////////////////// END OF ABSTRACT METHODS  /////////////////////////




    ///////////////////////// GETTERS  /////////////////////////

    public Deck getDeck() //returns the deck used in the game
    {
        return this.deck;
    }

    public Player getPlayer(int i) //gets player from index i
    {
        return this.players.get(i);
    }


    public int getNumPlayers() //returns number of players;
    {
        return NumPlayers;
    }

    ///////////////////////// END OF GETTERS  /////////////////////////


}
