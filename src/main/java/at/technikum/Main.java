package at.technikum;

public class Main {

    public static void main(String[] args) {

        Card monster1 = new Card("Water Wizzard", 15, CardElementType.WATER, CardMonsterType.WIZZARD);
        Card monster2 = new Card("Fire Dragon", 25, CardElementType.FIRE, CardMonsterType.DRAGON);
        Card monster3 = new Card("Dumb Goblin", 10, CardElementType.NORMAL, CardMonsterType.GOBLIN);
        Card monster4 = new Card("Misguided Knight", 15, CardElementType.NORMAL, CardMonsterType.KNIGHT);

        Card spell1 = new Card("Water Mirror", 20, CardElementType.WATER, CardMonsterType.SPELL);
        Card spell2 = new Card("Fireball", 25, CardElementType.FIRE, CardMonsterType.SPELL);
        Card spell3 = new Card("Rain Arrows", 15, CardElementType.NORMAL, CardMonsterType.SPELL);
        Card spell4 = new Card("Hellfire", 50, CardElementType.FIRE, CardMonsterType.SPELL);

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

        System.out.println("PlAyer1");
        player1.getCardStack().printStack();
        System.out.println("PlAyer2");
        player2.getCardStack().printStack();

        Battle battle1 = new Battle(player1, player2);
        battle1.start();
        //Battle battle1 = new Battle(null, player2);

        //System.out.println("Deck player1:");
        System.out.println("PlAyer1");
        player1.getCardStack().printDeck();
        //System.out.println("Deck player2:");
        System.out.println("PlAyer2");
        player2.getCardStack().printDeck();

        //System.out.println(monster1.toString());
        //System.out.println(spell1.toString());


    }
}
