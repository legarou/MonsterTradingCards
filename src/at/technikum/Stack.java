package at.technikum;

import java.util.ArrayList;

public class Stack {
    private ArrayList<Card> stack;
    private ArrayList<Card> deck;

    public Stack() {
        this.stack = new ArrayList<>();
        this.deck = new ArrayList<>();
    }

    public ArrayList<Card> getStack() {
        return stack;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void addCardToStack(Card newCard) {
        stack.add(newCard);
    }

    public void addToDeck(Card newCard) {
        if (!newCard.getIsInDeck()) {
            deck.add(newCard);
            newCard.changeIsInDeck();
        } else {
            System.out.println("Card is already in deck!");
        }

    }

    public void removeFromDeck(Card oldCard) {
        if (oldCard.getIsInDeck()) {
            deck.remove(oldCard);
            oldCard.changeIsInDeck();
        }
    }

    public void printStack() {
        for (Card i : stack) {
            System.out.println("Card: " + i.getName());
        }
    }

    public void printDeck() {
        if (deck.size() != 0) {
            for (Card i : deck) {
                System.out.println("Card: " + i.getName());
            }
        } else {
            System.out.println("Deck is empty!");
        }
    }
}
