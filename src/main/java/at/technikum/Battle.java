package at.technikum;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private String playerOne;
    private List<Card> deckOne;
    private Card playCardOne;
    private String playerTwo;
    private List<Card> deckTwo;
    private Card playCardTwo;
    private int roundCounter;
    private BattleOutcome battleOutcome;

    // Constructor
    // does a max. of 100 rounds of one-on-one fights
    public Battle(@NotNull User userOne, @NotNull User userTwo) {
        this.playerOne = userOne.getUsername();
        //this.deckOne = new ArrayList<Card>(userOne.getCardStack().getDeck());
        this.deckOne = userOne.getCardStack().getDeck();
        this.playerTwo = userTwo.getUsername();
        //this.deckTwo = new ArrayList<Card>(userTwo.getCardStack().getDeck());
        this.deckTwo = userTwo.getCardStack().getDeck();
        this.roundCounter = 1;
        this.battleOutcome = BattleOutcome.DRAW;
        // check for NULL
        // check for deck size

    }

    public void start() {
        if (deckOne.size() == 4 && deckTwo.size() == 4) {
            System.out.println(playerOne + " VS " + playerTwo);

            while ((roundCounter <= 100) && !isGameOver()) {
                System.out.println("Round " + roundCounter + ":");
                fight();
                battlePostprocessing();
                roundCounter++;
            }
            proclaimWinner();
        } else {
            System.out.println("At least one of the decks has the wrong size! Battle cannot commence.");
        }
    }

    // executes the fight between two random cards from each deck
    private void fight() {
        playCardOne = returnRandom(deckOne);
        playCardTwo = returnRandom(deckTwo);

        System.out.println(playerOne + " plays: " + playCardOne.getName());
        System.out.println(playCardOne.toString());
        System.out.println(playerTwo + " plays: " + playCardTwo.getName());
        System.out.println(playCardTwo.toString());

        if (playCardOne instanceof Monster) {
            if (playCardTwo instanceof Monster) // element irrelevant
            {
                System.out.println("Both players played a Monster card! Element type will have no effect during this round.");
                monsterFight((Monster) playCardOne, (Monster) playCardTwo);
            } else {
                mixedFight();
            }
        } else if (playCardTwo instanceof Monster) {
            mixedFight();
        } else // two spells
        {
            elementFight();
        }
    }

    private void monsterFight(Monster playCardOne, Monster playCardTwo) {
        /*    • Goblins are too afraid of Dragons to attack.
              • Wizzard can control Orks so they are not able to damage them.
              • The FireElves know Dragons since they were little and can evade their attacks.
        */
        if (MonsterType.DRAGON == playCardOne.getMonsterType()) {
            if (MonsterType.GOBLIN == playCardTwo.getMonsterType()) {
                System.out.println("Goblins are too afraid of Dragons to attack!");
                System.out.println(playCardTwo.getName() + " cowers as " + playCardOne.getName() + " slays them!");
                System.out.println(playerOne + " won this round!");
                battleOutcome = BattleOutcome.PLAYER1;
            } else if (MonsterType.FIREELF == playCardTwo.getMonsterType()) {
                System.out.println("The FireElves know Dragons since they were little and can evade their attacks.");
                System.out.println(playCardTwo.getName() + " evades " + playCardOne.getName() + "'s attack skillfully and slays them with ease!");
                System.out.println(playerTwo + " won this round!");
                battleOutcome = BattleOutcome.PLAYER2;
            } else {
                System.out.println("This will be a pure fight of power!");
                battleOutcome = BattleOutcome.DRAW;
                trumpFight();
            }
        } else if (MonsterType.DRAGON == playCardTwo.getMonsterType()) {
            if (MonsterType.GOBLIN == playCardOne.getMonsterType()) {
                System.out.println("Goblins are too afraid of Dragons to attack!");
                System.out.println(playCardOne.getName() + " cowers as " + playCardTwo.getName() + " slays them!");
                System.out.println(playerTwo + " won this round!");
                battleOutcome = BattleOutcome.PLAYER2;
            } else if (MonsterType.FIREELF == playCardOne.getMonsterType()) {
                System.out.println("The FireElves know Dragons since they were little and can evade their attacks.");
                System.out.println(playCardOne.getName() + " evades " + playCardTwo.getName() + "'s attack skillfully and slays them with ease!");
                System.out.println(playerOne + " won this round!");
                battleOutcome = BattleOutcome.PLAYER1;
            } else {
                System.out.println("This will be a pure fight of power!");
                battleOutcome = BattleOutcome.DRAW;
                trumpFight();
            }
        } else if (MonsterType.WIZZARD == playCardOne.getMonsterType() && MonsterType.ORK == playCardTwo.getMonsterType()) {
            System.out.println("Wizzards can control Orks! Orks are unable to attack or defend themselves.");
            System.out.println(playCardTwo.getName() + " is unable to move! " + playCardOne.getName() + "slays them with ease!");
            System.out.println(playerOne + " won this round!");
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (MonsterType.ORK == playCardOne.getMonsterType() && MonsterType.WIZZARD == playCardTwo.getMonsterType()) {
            System.out.println("Wizzards can control Orks! Orks are unable to attack or defend themselves.");
            System.out.println(playCardOne.getName() + " is unable to move! " + playCardTwo.getName() + "slays them with ease!");
            System.out.println(playerTwo + " won this round!");
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            System.out.println("This will be a pure fight of power!");
            battleOutcome = BattleOutcome.DRAW;
            trumpFight();
        }

    }

    private void mixedFight() { //Monster monsterCard, Spell spellCard
        /*    • The armor of Knights is so heavy that WaterSpells make them drown them instantly.
              • The Kraken is immune against spells.
        */

        if(playCardOne instanceof Monster) {
            if (MonsterType.KNIGHT == ((Monster) playCardOne).getMonsterType() && ElementType.WATER == playCardTwo.getElementType()) {
                System.out.println(playCardOne.getName() + "'s armor is too heavy!! Any WaterSpells make them drown instantly.");
                battleOutcome = BattleOutcome.PLAYER2;
            } else if (MonsterType.KRAKEN == ((Monster) playCardOne).getMonsterType()) {
                System.out.println("The Kraken is immune against spells!");
                battleOutcome = BattleOutcome.PLAYER1;
            } else {
                if (((Monster) playCardOne).getElementType() != playCardTwo.getElementType()) {
                    elementFight();
                } else {
                    System.out.println("Both Cards have the same ElementType! No trumps this round.");
                    battleOutcome = BattleOutcome.DRAW;
                    trumpFight();
                }
            }
        }
        else { // playCardTwo instanceof Monster
            if (MonsterType.KNIGHT == ((Monster) playCardTwo).getMonsterType() && ElementType.WATER == playCardOne.getElementType()) {
                System.out.println(((Monster) playCardTwo).getName() + "'s armor is too heavy!! Any WaterSpells make them drown instantly.");
                battleOutcome = BattleOutcome.PLAYER1;
            } else if (MonsterType.KRAKEN == ((Monster) playCardTwo).getMonsterType()) {
                System.out.println("The Kraken is immune against spells!");
                battleOutcome = BattleOutcome.PLAYER2;
            } else {
                if (((Monster) playCardTwo).getElementType() != playCardOne.getElementType()) {
                    elementFight();
                } else {
                    System.out.println("Both Cards have the same ElementType! No trumps this round.");
                    battleOutcome = BattleOutcome.DRAW;
                    trumpFight();
                }
            }
        }

    }

    // if elements are not equal
    private void elementFight() {
        /*      • water -> fire
                • fire -> normal
                • normal -> water
         */
        if (ElementType.WATER == playCardOne.getElementType()) {
            if (ElementType.FIRE == playCardTwo.getElementType()) {
                System.out.println("Water trumps Fire!");
                //playerOneElementTrump();
                battleOutcome = BattleOutcome.PLAYER1;
                trumpFight();
            } else if (ElementType.NORMAL == playCardTwo.getElementType()) {
                System.out.println("Normal trumps Water!");
                //playerTwoElementTrump();
                battleOutcome = BattleOutcome.PLAYER2;
                trumpFight();
            }
        } else if (ElementType.FIRE == playCardOne.getElementType()) {
            if (ElementType.NORMAL == playCardTwo.getElementType()) {
                System.out.println("Fire trumps Normal!");
                //playerOneElementTrump();
                battleOutcome = BattleOutcome.PLAYER1;
                trumpFight();
            } else if (ElementType.WATER == playCardTwo.getElementType()) {
                System.out.println("Water trumps Fire!");
                //playerTwoElementTrump();
                battleOutcome = BattleOutcome.PLAYER2;
                trumpFight();
            }
        } else if (ElementType.NORMAL == playCardOne.getElementType()) {
            if (ElementType.WATER == playCardTwo.getElementType()) {
                System.out.println("Normal trumps Water!");
                //playerOneElementTrump();
                battleOutcome = BattleOutcome.PLAYER1;
                trumpFight();
            } else if (ElementType.FIRE == playCardTwo.getElementType()) {
                System.out.println("Fire trumps Normal!");
                //playerTwoElementTrump();
                battleOutcome = BattleOutcome.PLAYER2;
                trumpFight();
            }
        } else {
            // else equal but is checked before
            battleOutcome = BattleOutcome.DRAW;
            trumpFight();
        }

    }

    private void trumpFight() {
        int playerOnePower = playCardOne.getDamage();
        int playerTwoPower = playCardTwo.getDamage();
        switch (battleOutcome) {
            case DRAW -> {
                break;
            }
            case PLAYER1 -> {
                System.out.println(playCardOne.getName() + " gets their attack doubled, while the attack of " + playCardTwo.getName() + " gets halved!");
                playerOnePower *= 2;
                playerTwoPower /= 2;
                break;
            }
            case PLAYER2 -> {
                System.out.println(playCardTwo.getName() + " gets their attack doubled, while the attack of " + playCardOne.getName() + " gets halved!");
                playerOnePower /= 2;
                playerTwoPower *= 2;
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }

        System.out.println(playerOne + " with " + playerOnePower + " damage VS " + playerTwo + " with " + playerTwoPower);
        if (playerOnePower > playerTwoPower) {
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (playerOnePower < playerTwoPower) {
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            battleOutcome = BattleOutcome.DRAW;
        }

    }

    // if one player won, it takes the others losing card from the losers deck and puts it into the winners
    private void battlePostprocessing() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("It's a draw! Both cards are matched in power!");
                break;
            }
            case PLAYER1 -> {
                System.out.println(playerOne + " wins this round!");
                System.out.println("Removing " + playCardTwo.getName() + " from " + playerTwo + "'s deck and adding it to deck of " + playerOne + ".");
                if (!deckTwo.remove(playCardTwo)) {
                    // THROW SOME ERROR; IT BROKE
                    System.out.println("PROBLEM!!!");
                }
                deckOne.add(playCardTwo);
                System.out.println(playerOne + " now has " + deckOne.size() + " cards.");
                System.out.println(playerTwo + " now has " + deckTwo.size() + " cards.");
                break;
            }
            case PLAYER2 -> {
                System.out.println(playerTwo + " wins this round!");
                System.out.println("Removing " + playCardOne.getName() + " from " + playerOne + "'s deck and adding it to deck of " + playerTwo + ".");
                if (!deckOne.remove(playCardOne)) {
                    // THROW SOME ERROR; IT BROKE
                    System.out.println("PROBLEM!!!");
                }
                deckTwo.add(playCardOne);
                System.out.println(playerOne + " now has " + deckOne.size() + " cards in their deck.");
                System.out.println(playerTwo + " now has " + deckTwo.size() + " cards in their deck.");
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }

    }

    // returns random number between 0 and deck.size()-1
    private Card returnRandom(@NotNull List<Card> deck) {
        int randNum = ThreadLocalRandom.current().nextInt(0, deck.size());
        return deck.get(randNum);
    }

    // returns true if any deck is empty
    private boolean isGameOver() {
        if (deckOne.size() == 0 || deckTwo.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void proclaimWinner() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("Both players are matched in might and wit! No winners this round!");
                break;
            }
            case PLAYER1 -> {
                System.out.println(playerOne + " wins! Congratulations!");
                break;
            }
            case PLAYER2 -> {
                System.out.println(playerTwo + " wins! Congratulations!");
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }
    }
}
