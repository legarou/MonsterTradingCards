package at.technikum;

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
    public Battle(User userOne, User userTwo) {
        this.playerOne = userOne.getUsername();
        this.deckOne = userOne.getCardStack().getDeck();
        this.playerTwo = userTwo.getUsername();
        this.deckTwo = userTwo.getCardStack().getDeck();
        this.roundCounter = 1;
        this.battleOutcome = BattleOutcome.DRAW;
    }

    public boolean start() {
        if (deckOne.size() == 4 && deckTwo.size() == 4) {
            System.out.println(playerOne + " VS " + playerTwo);

            while ((roundCounter <= 100) && !isGameOver()) {
                System.out.println("Round " + roundCounter + ":");
                oneRoundFight();
                battlePostprocessing();
                roundCounter++;
            }
            proclaimWinner();
            return true;
        } else {
            System.out.println("At least one of the decks has the wrong size! Battle cannot commence.");
            return false;
        }
    }

    // executes the fight between two random cards from each deck
    private BattleOutcome oneRoundFight() {
        playCardOne = returnRandom(deckOne);
        playCardTwo = returnRandom(deckTwo);
        int specialCasesResult;

        System.out.println(playerOne + " plays: " + playCardOne.getName());
        System.out.println(playCardOne.toString());
        System.out.println(playerTwo + " plays: " + playCardTwo.getName());
        System.out.println(playCardTwo.toString());

        BattleSpecialCases checkCases = new BattleSpecialCases(playCardOne.getMonsterType(), playCardOne.getElementType(), playCardTwo.getMonsterType(), playCardTwo.getElementType());
        specialCasesResult = checkCases.checkForSpecialCase();

        switch(specialCasesResult) {
            case 0:
                elementFight();
                break;
            case 1:
                System.out.println(checkCases.getMessage());
                System.out.println(playerOne + " won this round!");
                battleOutcome = BattleOutcome.PLAYER1;
                break;
            case 2:
                System.out.println(checkCases.getMessage());
                System.out.println(playerTwo + " won this round!");
                battleOutcome = BattleOutcome.PLAYER2;
                break;
            case 3:
                System.out.println("Both players played a Monster card! Element type will have no effect during this round.\nThis will be a pure fight of power!");
                battleOutcome = BattleOutcome.DRAW;
                cardFight();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + specialCasesResult);
        }
        return battleOutcome;
    }

    // if elements are not equal
    public BattleOutcome elementFight() {

        int elementTrumpResult;
        BattleElements checkElementTrump = new BattleElements(playCardOne.getElementType(), playCardTwo.getElementType());
        elementTrumpResult = checkElementTrump.checkForTrump();

        switch(elementTrumpResult) {
            case 0:
                battleOutcome = BattleOutcome.DRAW;
                break;
            case 1:
                System.out.println(checkElementTrump.getMessage());
                battleOutcome = BattleOutcome.PLAYER1;
                break;
            case 2:
                System.out.println(checkElementTrump.getMessage());
                battleOutcome = BattleOutcome.PLAYER2;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + elementTrumpResult);
        }

        cardFight();
        return battleOutcome;
    }

    private BattleOutcome cardFight() {
        int playerOnePower;
        int playerTwoPower;

        // whether any has trump over the other
        switch (battleOutcome) {
            case DRAW -> {
                playerOnePower = playCardOne.getDamage();
                playerTwoPower = playCardTwo.getDamage();
                break;
            }
            case PLAYER1 -> {
                System.out.println(playCardOne.getName() + " gets their attack doubled, while the attack of " + playCardTwo.getName() + " gets halved!");
                playerOnePower = playCardOne.getDamage() * 2;
                playerTwoPower = playCardTwo.getDamage() / 2;
                break;
            }
            case PLAYER2 -> {
                System.out.println(playCardTwo.getName() + " gets their attack doubled, while the attack of " + playCardOne.getName() + " gets halved!");
                playerOnePower = playCardOne.getDamage() / 2;
                playerTwoPower = playCardTwo.getDamage() * 2;
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

        return battleOutcome;

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
                    throw new IllegalStateException("Unexpected: could not remove card from deck!");
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
                    throw new IllegalStateException("Unexpected: could not remove card from deck!");
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
    private Card returnRandom(List<Card> deck) {
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
