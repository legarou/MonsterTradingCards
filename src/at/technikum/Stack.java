package at.technikum;

import java.util.ArrayList;

public class Stack {
    private ArrayList<Card> stack;
    private ArrayList<Card> deck;

    public Stack() {
        this.stack = new ArrayList<>();
        this.deck  = new ArrayList<>();
    }

    public ArrayList<Card> getStack() {
        return stack;
    }

    public ArrayList<Card> getDeck() {
        if(deck.size() != 4) {
            return null;
        }
        else {
            return deck;
        }
    }

    public void addCardToStack(Card newCard) {
        stack.add(newCard);
    }

    public void addToDeck(Card newCard) {
        deck.add(newCard);
    }

    public void removeFromDeck(Card oldCard) {
        for (Card i : deck)
        {
            if(i.getName() == oldCard.getName()) {
                deck.remove(i);
                break;
            }
        }
    }
}
