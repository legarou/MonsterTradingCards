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
        return new ArrayList<>(stack);
    }

    public ArrayList<Card> getDeck() {
        return new ArrayList<>(deck);
    }

    public void addCardToStack(Card newCard) {
        stack.add(newCard);
    }

    public void addToDeck(Card newCard) {
        if (!deck.contains(newCard) && stack.contains(newCard)) {
            deck.add(newCard);
        } else {
            System.out.println("Card is already in deck!");
        }
    }

    public void removeFromDeck(Card oldCard) {
        deck.remove(oldCard);
    }

    public void printStack() {
        for (Card i : stack) {
            System.out.println("Card: " + i.getName());
        }
    }

    public void printDeck() {
        for (Card i : deck) {
            System.out.println("Card: " + i.getName());
        }
    }
}
