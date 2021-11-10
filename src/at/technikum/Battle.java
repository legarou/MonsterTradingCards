package at.technikum;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private User playerOne;
    private ArrayList<Card> deckOne;
    private Card playCardOne;
    private User playerTwo;
    private ArrayList<Card> deckTwo;
    private Card playCardTwo;
    private Integer roundCounter;
    private BattleOutcome battleOutcome;

    // Constructor
    // does a max. of 100 rounds of fights
    public Battle(@NotNull User playerOne, @NotNull User playerTwo) {
        if (playerOne.getCardStack().getDeck().size() == 4 && playerTwo.getCardStack().getDeck().size() == 4) {
            this.playerOne = playerOne;
            this.deckOne = new ArrayList<Card>(playerOne.getCardStack().getDeck());
            this.playerTwo = playerTwo;
            this.deckTwo = new ArrayList<Card>(playerTwo.getCardStack().getDeck());
            this.roundCounter = 1;
            this.battleOutcome = BattleOutcome.DRAW;
            // check for NULL
            // check for deck size

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

    // returns true if any deck is empty
    private Boolean isGameOver() {
        if (deckOne.size() == 0 || deckTwo.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // returns random number between 0 and deck.size()-1
    private Card returnRandom(@NotNull ArrayList<Card> deck) {
        int randNum = ThreadLocalRandom.current().nextInt(0, deck.size());
        return deck.get(randNum);
    }

    // if one player won, it takes the others losing card from the losers deck and puts it into the winners
    private void battlePostprocessing() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("It's a draw! Both cards are matched in power!");
                break;
            }
            case PLAYER1 -> {
                System.out.println(playerOne.getUsername() + " wins this round!");
                System.out.println("Removing " + playCardTwo.getName() + " from " + playerTwo.getUsername() + "'s deck and adding it to deck of " + playerOne.getUsername() + ".");
                if (!deckTwo.remove(playCardTwo)) {
                    // THROW SOME ERROR; IT BROKE
                    System.out.println("PROBLEM!!!");
                }
                deckOne.add(playCardTwo);
                System.out.println(playerOne.getUsername() + " now has " + deckOne.size() + " cards.");
                System.out.println(playerTwo.getUsername() + " now has " + deckTwo.size() + " cards.");
                break;
            }
            case PLAYER2 -> {
                System.out.println(playerTwo.getUsername() + " wins this round!");
                System.out.println("Removing " + playCardOne.getName() + " from " + playerOne.getUsername() + "'s deck and adding it to deck of " + playerTwo.getUsername() + ".");
                if (!deckOne.remove(playCardOne)) {
                    // THROW SOME ERROR; IT BROKE
                    System.out.println("PROBLEM!!!");
                }
                deckTwo.add(playCardOne);
                System.out.println(playerOne.getUsername() + " now has " + deckOne.size() + " cards in their deck.");
                System.out.println(playerTwo.getUsername() + " now has " + deckTwo.size() + " cards in their deck.");
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }

    }

    // executes the fight between two cards
    private void fight() {
        playCardOne = returnRandom(deckOne);
        playCardTwo = returnRandom(deckTwo);

        System.out.println(playerOne.getUsername() + " plays: " + playCardOne.getName());
        System.out.println(playCardOne.getDescription());
        System.out.println(playerTwo.getUsername() + " plays: " + playCardTwo.getName());
        System.out.println(playCardTwo.getDescription());

        if (playCardOne instanceof Monster) {
            if (playCardTwo instanceof Monster) // element irrelevant
            {
                System.out.println("Both players played a Monster card! Element type does not matter in this round.");
                monsterFight((Monster) playCardOne, (Monster) playCardTwo);
            } else {
                mixedFight((Monster) playCardOne, (Spell) playCardTwo);
            }
        } else if (playCardTwo instanceof Monster) {
            mixedFight((Monster) playCardTwo, (Spell) playCardOne);
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
                System.out.println(playerOne.getUsername() + " won this round!");
                battleOutcome = BattleOutcome.PLAYER1;
            } else if (MonsterType.FIREELF == playCardTwo.getMonsterType()) {
                System.out.println("The FireElves know Dragons since they were little and can evade their attacks.");
                System.out.println(playCardTwo.getName() + " evades " + playCardOne.getName() + "'s attack skillfully and slays them with ease!");
                System.out.println(playerTwo.getUsername() + " won this round!");
                battleOutcome = BattleOutcome.PLAYER2;
            } else {
                System.out.println("This will a pure fight of power!");
                noTrumps();
            }
        } else if (MonsterType.DRAGON == playCardTwo.getMonsterType()) {
            if (MonsterType.GOBLIN == playCardOne.getMonsterType()) {
                System.out.println("Goblins are too afraid of Dragons to attack!");
                System.out.println(playCardOne.getName() + " cowers as " + playCardTwo.getName() + " slays them!");
                System.out.println(playerTwo.getUsername() + " won this round!");
                battleOutcome = BattleOutcome.PLAYER2;
            } else if (MonsterType.FIREELF == playCardOne.getMonsterType()) {
                System.out.println("The FireElves know Dragons since they were little and can evade their attacks.");
                System.out.println(playCardOne.getName() + " evades " + playCardTwo.getName() + "'s attack skillfully and slays them with ease!");
                System.out.println(playerOne.getUsername() + " won this round!");
                battleOutcome = BattleOutcome.PLAYER1;
            } else {
                System.out.println("This will a pure fight of power!");
                noTrumps();
            }
        } else if (MonsterType.WIZZARD == playCardOne.getMonsterType() && MonsterType.ORK == playCardTwo.getMonsterType()) {
            System.out.println("Wizzards can control Orks! Orks are unable to attack or defend themselves.");
            System.out.println(playCardTwo.getName() + " is unable to move! " + playCardOne.getName() + "slays them with ease!");
            System.out.println(playerOne.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (MonsterType.ORK == playCardOne.getMonsterType() && MonsterType.WIZZARD == playCardTwo.getMonsterType()) {
            System.out.println("Wizzards can control Orks! Orks are unable to attack or defend themselves.");
            System.out.println(playCardOne.getName() + " is unable to move! " + playCardTwo.getName() + "slays them with ease!");
            System.out.println(playerTwo.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            System.out.println("This will a pure fight of power!");
            noTrumps();
        }

    }

    private void mixedFight(Monster monsterCard, Spell spellCard) {
        /*    • The armor of Knights is so heavy that WaterSpells make them drown them instantly.
              • The Kraken is immune against spells.
        */
        if (MonsterType.KNIGHT == monsterCard.getMonsterType() && ElementType.WATER == spellCard.getElementType()) {
            System.out.println(monsterCard.getName() + "'s armor is so heavy!! Any WaterSpells make them drown instantly.");
            spellWins();
        } else if (MonsterType.KRAKEN == monsterCard.getMonsterType()) {
            System.out.println("The Kraken is immune against spells!");
            monsterWins();
        } else {
            if (monsterCard.getElementType() != spellCard.getElementType()) {
                elementFight();
            } else {
                System.out.println("Both Cards have the same ElementType! No trumps this round.");
                noTrumps();
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
                playerOneElementTrump();
            } else if (ElementType.NORMAL == playCardTwo.getElementType()) {
                System.out.println("Normal trumps Water!");
                playerTwoElementTrump();
            }
        } else if (ElementType.FIRE == playCardOne.getElementType()) {
            if (ElementType.NORMAL == playCardTwo.getElementType()) {
                System.out.println("Fire trumps Normal!");
                playerOneElementTrump();
            } else if (ElementType.WATER == playCardTwo.getElementType()) {
                System.out.println("Water trumps Fire!");
                playerTwoElementTrump();
            }
        } else if (ElementType.NORMAL == playCardOne.getElementType()) {
            if (ElementType.WATER == playCardTwo.getElementType()) {
                System.out.println("Normal trumps Water!");
                playerOneElementTrump();
            } else if (ElementType.FIRE == playCardTwo.getElementType()) {
                System.out.println("Fire trumps Normal!");
                playerTwoElementTrump();
            }
        } else {
            // else equal but is checked before
            noTrumps();
        }

    }

    private void playerOneElementTrump() {
        // MESSAGE: DAMAGE AMOUNT AND EVERYTHING
        System.out.println(playCardOne.getName() + " gets their attack doubled, while the attack of " + playCardTwo.getName() + " gets halved!");
        System.out.println(playerOne.getUsername() + " with " + playCardOne.getDamage() * 2 + " damage VS " + playerTwo.getUsername() + " with " + playCardTwo.getDamage() / 2);
        if (playCardOne.getDamage() * 2 > playCardTwo.getDamage() / 2) {
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (playCardOne.getDamage() * 2 < playCardTwo.getDamage() / 2) {
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            battleOutcome = BattleOutcome.DRAW;
        }
    }

    private void playerTwoElementTrump() {
        // MESSAGE: DAMAGE AMOUNT AND EVERYTHING
        System.out.println(playCardTwo.getName() + " gets their attack doubled, while the attack of " + playCardOne.getName() + " gets halved!");
        System.out.println(playerOne.getUsername() + " with " + playCardOne.getDamage() / 2 + " damage VS " + playerTwo.getUsername() + " with " + playCardTwo.getDamage() * 2);
        if (playCardOne.getDamage() / 2 > playCardTwo.getDamage() * 2) {
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (playCardOne.getDamage() / 2 < playCardTwo.getDamage() * 2) {
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            battleOutcome = BattleOutcome.DRAW;
        }
    }

    private void noTrumps() {
        // MESSAGE: DAMAGE AMOUNT AND EVERYTHING
        System.out.println(playerOne.getUsername() + " with " + playCardOne.getDamage() + " damage VS " + playerTwo.getUsername() + " with " + playCardTwo.getDamage());
        if (playCardOne.getDamage() > playCardTwo.getDamage()) {
            battleOutcome = BattleOutcome.PLAYER1;
        } else if (playCardOne.getDamage() < playCardTwo.getDamage()) {
            battleOutcome = BattleOutcome.PLAYER2;
        } else {
            battleOutcome = BattleOutcome.DRAW;
        }
    }

    private void monsterWins() {
        if (playCardOne instanceof Monster) {
            System.out.println(playerOne.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER1;
        } else {
            System.out.println(playerTwo.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER2;
        }
    }

    private void spellWins() {
        if (playCardOne instanceof Spell) {
            System.out.println(playerOne.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER1;
        } else {
            System.out.println(playerTwo.getUsername() + " won this round!");
            battleOutcome = BattleOutcome.PLAYER2;
        }
    }

    private void proclaimWinner() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("Both players are matched in might and wit! No winners this round!");
                break;
            }
            case PLAYER1 -> {
                System.out.println(playerOne.getUsername() + " wins! Congratulations!");
                break;
            }
            case PLAYER2 -> {
                System.out.println(playerTwo.getUsername() + " wins! Congratulations!");
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }
    }
}
