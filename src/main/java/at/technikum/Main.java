package at.technikum;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        Monster monster1 = new Monster("Water Wizzard", 15, ElementType.WATER, MonsterType.WIZZARD);
        Monster monster2 = new Monster("Fire Dragon", 25, ElementType.FIRE, MonsterType.DRAGON);
        Monster monster3 = new Monster("Dumb Goblin", 10, ElementType.NORMAL, MonsterType.GOBLIN);
        Monster monster4 = new Monster("Misguided Knight", 15, ElementType.NORMAL, MonsterType.KNIGHT);

        Spell spell1 = new Spell("Water Mirror", 20, ElementType.WATER);
        Spell spell2 = new Spell("Fireball", 25, ElementType.FIRE);
        Spell spell3 = new Spell("Rain Arrows", 15, ElementType.NORMAL);
        Spell spell4 = new Spell("Hellfire", 50, ElementType.FIRE);

        User player1 = new User("Edmund");
        User player2 = new User("Elizabeth");

        Stack stack1 = player1.getCardStack();
        Stack stack2 = player2.getCardStack();

        stack1.addCardToStack(monster1);
        stack1.addCardToStack(monster3);
        stack1.addCardToStack(spell4);
        stack1.addCardToStack(spell3);
        stack1.addToDeck(monster1);
        stack1.addToDeck(monster3);
        stack1.addToDeck(spell4);
        stack1.addToDeck(spell3);

        stack2.addCardToStack(monster2);
        stack2.addCardToStack(monster4);
        stack2.addCardToStack(spell1);
        stack2.addCardToStack(spell2);
        stack2.addToDeck(monster2);
        stack2.addToDeck(monster4);
        stack2.addToDeck(spell1);
        stack2.addToDeck(spell2);

        System.out.println("PLAyer1");
        player1.getCardStack().printStack();
        System.out.println("PLAyer2");
        player2.getCardStack().printStack();

        Battle battle1 = new Battle(player1, player2);
        battle1.start();
        //Battle battle1 = new Battle(null, player2);

        //System.out.println("Deck player1:");
        System.out.println("PLAyer1");
        player1.getCardStack().printDeck();
        //System.out.println("Deck player2:");
        System.out.println("PLAyer2");
        player2.getCardStack().printDeck();

        //System.out.println(monster1.toString());
        //System.out.println(spell1.toString());


    }
}
