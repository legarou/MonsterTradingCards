package at.technikum.Battle;

import at.technikum.Cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private String playerOne;
    @Getter
    private List<Card> deckOne;
    @Setter
    private Card playCardOne;
    private String playerTwo;
    @Getter
    private List<Card> deckTwo;
    @Setter
    private Card playCardTwo;
    private int roundCounter;
    @Setter
    private BattleOutcome battleOutcome;
    @Getter
    private HashMap<String, String> logger = new HashMap<>();
    private String logKey;
    private String logForRound;

    // Constructor
    // does a max. of 100 rounds of one-on-one fights
    public Battle(String userOne, List<Card> deckOne, String userTwo, List<Card> deckTwo) {
        this.playerOne = userOne;
        this.deckOne = deckOne;
        this.playerTwo = userTwo;
        this.deckTwo = deckTwo;
        this.roundCounter = 1;
        this.battleOutcome = BattleOutcome.DRAW;
    }

    public boolean start() {
        if (deckOne.size() == 4 && deckTwo.size() == 4) {
            System.out.println(playerOne + " VS " + playerTwo);

            while ((roundCounter <= 100) && !isGameOver()) {
                System.out.println("Round " + roundCounter + ":");
                logKey = "round" + roundCounter;
                logForRound ="";
                oneRoundFight();
                // ADD LOGGER TO PROCESSING
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
    public BattleOutcome oneRoundFight() {
        // unique feature: roll dice for boost, 10% chance -> give all cards in deck +5-10(random) damage
        rollDiceForBoost(playerOne, deckOne);
        rollDiceForBoost(playerTwo, deckTwo);

        playCardOne = returnRandom(deckOne);
        playCardTwo = returnRandom(deckTwo);
        int specialCasesResult;

        logForRound = logForRound + playerOne + " plays: " + playCardOne.getName() + "\n" + playCardOne.toString()  + "\n";
        logForRound = logForRound + playerTwo + " plays: " + playCardTwo.getName() + "\n" + playCardTwo.toString()  + "\n";

        System.out.println(playerOne + " plays: " + playCardOne.getName());
        System.out.println(playCardOne.toString());
        System.out.println(playerTwo + " plays: " + playCardTwo.getName());
        System.out.println(playCardTwo.toString());

        BattleSpecialCases checkCases = new BattleSpecialCases(playCardOne.getCardMonsterType(), playCardOne.getElementType(), playCardTwo.getCardMonsterType(), playCardTwo.getElementType());
        specialCasesResult = checkCases.checkForSpecialCase();


        switch(specialCasesResult) {
            case 0:
                elementFight();
                break;
            case 1:
                System.out.println(checkCases.getMessage());
                System.out.println(playerOne + " won this round!");
                logForRound = logForRound + playerOne + " won this round!"  + "\n";
                battleOutcome = BattleOutcome.PLAYER1;
                break;
            case 2:
                System.out.println(checkCases.getMessage());
                System.out.println(playerTwo + " won this round!");
                logForRound = logForRound + playerTwo + " won this round!"  + "\n";
                battleOutcome = BattleOutcome.PLAYER2;
                break;
            case 3:
                System.out.println("Both players played a Monster card! Element type will have no effect during this round.\nThis will be a pure fight of power!");
                logForRound = logForRound + "Both players played a Monster card! Element type will have no effect during this round.\nThis will be a pure fight of power!"  + "\n";
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
        BattleOutcome battleOutcome = this.battleOutcome;

        cardFight();
        return battleOutcome;
    }

    public BattleOutcome cardFight() {
        int playerOnePower;
        int playerTwoPower;

        // whether any has trump over the other
        switch (battleOutcome) {
            case DRAW -> {
                playerOnePower = playCardOne.getDamage();
                playerTwoPower = playCardTwo.getDamage();
            }
            case PLAYER1 -> {
                System.out.println(playCardOne.getName() + " gets their attack doubled, while the attack of " + playCardTwo.getName() + " gets halved!");
                logForRound = logForRound + playCardOne.getName() + " gets their attack doubled, while the attack of " + playCardTwo.getName() + " gets halved!"  + "\n";
                playerOnePower = playCardOne.getDamage() * 2;
                playerTwoPower = playCardTwo.getDamage() / 2;
            }
            case PLAYER2 -> {
                System.out.println(playCardTwo.getName() + " gets their attack doubled, while the attack of " + playCardOne.getName() + " gets halved!");
                logForRound = logForRound + playCardTwo.getName() + " gets their attack doubled, while the attack of " + playCardOne.getName() + " gets halved!"  + "\n";
                playerOnePower = playCardOne.getDamage() / 2;
                playerTwoPower = playCardTwo.getDamage() * 2;
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }

        System.out.println(playerOne + " with " + playerOnePower + " damage VS " + playerTwo + " with " + playerTwoPower);
        logForRound = logForRound + playerOne + " with " + playerOnePower + " damage VS " + playerTwo + " with " + playerTwoPower  + "\n";

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
    public void battlePostprocessing() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("It's a draw! Both cards are matched in power!");
                logForRound = logForRound + "It's a draw! Both cards are matched in power!"  + "\n";
            }
            case PLAYER1 -> {
                System.out.println(playerOne + " wins this round!");
                System.out.println("Removing " + playCardTwo.getName() + " from " + playerTwo + "'s deck and adding it to deck of " + playerOne + ".");
                logForRound = logForRound + playerOne + " wins this round!"  + "\n";
                logForRound = logForRound + "Removing " + playCardTwo.getName() + " from " + playerTwo + "'s deck and adding it to deck of " + playerOne + "."  + "\n";

                if (!deckTwo.remove(playCardTwo)) {
                    // THROW SOME ERROR; IT BROKE
                    throw new IllegalStateException("Unexpected: could not remove card from deck!");
                }
                deckOne.add(playCardTwo);
            }
            case PLAYER2 -> {
                System.out.println(playerTwo + " wins this round!");
                System.out.println("Removing " + playCardOne.getName() + " from " + playerOne + "'s deck and adding it to deck of " + playerTwo + ".");
                logForRound = logForRound + playerTwo + " wins this round!"  + "\n";
                logForRound = "Removing " + playCardOne.getName() + " from " + playerOne + "'s deck and adding it to deck of " + playerTwo + "."  + "\n";

                if (!deckOne.remove(playCardOne)) {
                    // THROW SOME ERROR; IT BROKE
                    throw new IllegalStateException("Unexpected: could not remove card from deck!");
                }
                deckTwo.add(playCardOne);
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }

        System.out.println(playerOne + " now has " + deckOne.size() + " cards in their deck.");
        System.out.println(playerTwo + " now has " + deckTwo.size() + " cards in their deck.");
        logForRound = logForRound + playerOne + " now has " + deckOne.size() + " cards in their deck."  + "\n";
        logForRound = logForRound + playerTwo + " now has " + deckTwo.size() + " cards in their deck."  + "\n";
        logger.put(logKey, logForRound);
        logForRound="";

    }

    // returns random number between 0 and deck.size()-1
    private Card returnRandom(List<Card> deck) {
        int randNum = ThreadLocalRandom.current().nextInt(0, deck.size());
        return deck.get(randNum);
    }

    // returns true if any deck is empty
    public boolean isGameOver() {

        return (deckOne.size() == 0 || deckTwo.size() == 0);
    }

    private void proclaimWinner() {
        switch (battleOutcome) {
            case DRAW -> {
                System.out.println("Both players are matched in might and wit! No winners this round!");
                logger.put("result", "Both players are matched in might and wit! No winners this round!");
                logger.put("winner", "draw");
                logger.put("loser", "draw");
            }
            case PLAYER1 -> {
                System.out.println(playerOne + " wins! Congratulations!");
                logger.put("result", playerOne + " wins! Congratulations!");
                logger.put("winner", playerOne);
                logger.put("loser", playerTwo);
            }
            case PLAYER2 -> {
                System.out.println(playerTwo + " wins! Congratulations!");
                logger.put("result", playerTwo + " wins! Congratulations!");
                logger.put("winner", playerTwo);
                logger.put("loser", playerOne);
            }
            default -> throw new IllegalStateException("Unexpected value: " + battleOutcome);
        }
    }

    private void rollDiceForBoost(String username, List<Card> deck) {
        int random = ThreadLocalRandom.current().nextInt(0, 10);
        logForRound = logForRound + username + " rolls dice: ";
        if(random == (username.length()%10)) {
            for(Card card : deck) {
                random = ThreadLocalRandom.current().nextInt(5, 11);
                card.boostDamage(random);
                System.out.println(card.getName() + " gets " + random + " damage!");
            }
            logForRound = logForRound + "and wins a boost! Their cards get extra damage!\n";
        }
        else {
            logForRound = logForRound + "but does not win a boost! Maybe next round.\n";
        }
    }
}
