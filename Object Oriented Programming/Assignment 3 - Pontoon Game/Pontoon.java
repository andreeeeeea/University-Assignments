package com.bham.pij.assignments.pontoon;
// Sorina Andreea Ghimpu 2196670

public class Pontoon extends CardGame{

    ///////////////////////// PONTOON CONSTRUCTOR /////////////////////////

    public Pontoon(int nplayers) {
        super(nplayers);
    }

    ///////////////////////// END OF PONTOON CONSTRUCTOR /////////////////////////




    ///////////////////////// DEALING INITIAL CARDS /////////////////////////

    @Override
    public void dealInitialCards() {
        int i;
        for(i = 0; i<getNumPlayers(); i++)
        {
            getPlayer(i).dealToPlayer(getDeck().dealRandomCard()); //deals a card to player i
            getPlayer(i).dealToPlayer(getDeck().dealRandomCard()); //deals a card to player i
        }
    }

    ///////////////////////// END OF DEALING INITIAL CARDS /////////////////////////




    ///////////////////////// COMPARING TWO HANDS /////////////////////////

    @Override
    public int compareHands(Player hand1, Player hand2)
    {
        /////////////////////////////// BUSTING CHECK ///////////////////////////////


        if(hand1.priorityCheck() == 0) //if hand 1 is a bust
        {
            if(hand2.priorityCheck() == 0) return 0; //if hand 2 is a bust too, it's a tie
            if(hand2.priorityCheck() !=0) return +1; //if hand 2 isn't a bust, hand 2 wins
        }

        if(hand2.priorityCheck() == 0) //if hand 2 is a bust
        {
            if(hand1.priorityCheck() == 0) return 0; //if hand 1 is a bust, it's a tie
            if(hand1.priorityCheck() != 0) return -1; //if hand 1 isnt a bust, hand 1 wins
        }


        /////////////////////////////// END OF BUSTING CHECK ///////////////////////////////

        /////////////////////////////// PONTOON CHECK ///////////////////////////////


        if(hand1.priorityCheck() == 4) //if hand 1 is a pontoon
        {
            if(hand2.priorityCheck() == 4) return 0; //if hand 2 is a pontoon, it's a tie
            else if(hand2.priorityCheck() !=4) return -1; //if hand 2 isnt a pontoon, hand 1 wins
        }

        if(hand2.priorityCheck() == 4) //if hand 2 is a pontoon
        {
            if(hand1.priorityCheck() == 4) return 0; //if hand 1 is a pontoon, it's a tie
            else if(hand1.priorityCheck() !=4) return +1; //if hand 1 isnt a pontoon, hand 2 wins
        }


        /////////////////////////////// END OF PONTOON CHECK ///////////////////////////////

        /////////////////////////////// FIVE CARD TRICK CHECK ///////////////////////////////

        if(hand1.priorityCheck() == 3) //if hand 1 is a FCT (five card trick)
        {
            if(hand2.priorityCheck() == 3) return 0; //if hand 2 is a fct, its a tie
            else if(hand2.priorityCheck() < 3) return -1; //if hand 2 isnt a fct, hand 1 wins
        }

        if(hand2.priorityCheck() == 3) //if hand 2 is a fct
        {
            if(hand1.priorityCheck() == 3) return 0; //if hand 1 is a fct, its a tie
            else if(hand1.priorityCheck() < 3) return +1; //if hand 1 isnt a fct, hand 2 wins
        }


        /////////////////////////////// END OF FIVE CARD TRICK CHECK ///////////////////////////////

        /////////////////////////////// TOTALLING 21 CHECK ///////////////////////////////


        if(hand1.priorityCheck() == 2) //if hand 1 totals 21
        {
            if(hand2.priorityCheck() == 2) return  0; //if hand 2 totals 21, its a tie
            else if(hand2.priorityCheck() < 2) return -1; //if hand 2 doesnt total 21, hand 1 wins
        }

        if(hand2.priorityCheck() == 2) //if hand 2 totals 21
        {
            if(hand1.priorityCheck() == 2) return 0; //if hand 1 totals 21, its a tie
            else if (hand1.priorityCheck() < 2) return +1; //if hand 1 doesnt total 21, hand 2 wins
        }


        /////////////////////////////// END OF TOTALLING 21 CHECK ///////////////////////////////

        /////////////////////////////// TOTALLING 20 OR LESS CHECK ///////////////////////////////


        if(hand1.priorityCheck() == 1) //if hand 1 totals 20 or less
        {
            if(hand2.priorityCheck() == 1) //if hand 2 totals 20 or less too
            {
                if(hand1.getActualBestNumericalHandValue() > hand2.getActualBestNumericalHandValue()) return -1;
                //if hand 1's sum is closer to 21 than hand 2's, hand 1 wins
                else if(hand1.getActualBestNumericalHandValue() < hand2.getActualBestNumericalHandValue()) return +1;
                //if hand 2's sum is closer to 21 than hand 1's, hand 2 wins
                     else if(hand1.getActualBestNumericalHandValue() == hand2.getActualBestNumericalHandValue()) return 0;
                //if both hands have the same sum, it's a tie
            }
        }

        /////////////////////////////// END OF TOTALLING 20 OR LESS CHECK ///////////////////////////////

        return 0;
    }
    // compares 2 hands
    // if hand 1 is better than hand 2 => -1
    // if hand 2 is better than hand 1 => +1
    // if hand 1 == hand 2 => 0

    ///////////////////////// END OF COMPARING TWO HANDS /////////////////////////

}
