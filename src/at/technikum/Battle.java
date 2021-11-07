package at.technikum;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private User playerOne;
    private ArrayList<Card> deckOne;
    private Card playCardOne;
    private User playerTwo;
    private ArrayList<Card> deckTwo;
    private Card playCardTwo;


    public Battle(User playerOne, User playerTwo) {
        this.playerOne = playerOne;
        this.deckOne = playerOne.getCardStack().getDeck();
        this.playerTwo = playerTwo;
        this.deckTwo = playerTwo.getCardStack().getDeck();
        // check for NULL
        // check for deck size
    }

    private Card returnRandom(ArrayList<Card> deck) {
        int randNum = ThreadLocalRandom.current().nextInt(0, deck.size() + 1);
        return deck.get(randNum);
    }

    //returns winner, 1 for playerOne and 2 for playerTwo
    public Integer executeBattle() {
        while(deckOne.size() > 0 && deckTwo.size() > 0)
        {

        }

        return -1;
    }

    //returns winner, 1 for playerOne and 2 for playerTwo
    public Integer fight() {
        playCardOne = returnRandom(deckOne);
        playCardTwo = returnRandom(deckTwo);

        if(playCardOne instanceof Monster) // element irrelevant
        {
            if(playCardTwo instanceof Monster)
            {
                return monsterFight((Monster)playCardOne, (Monster)playCardTwo);
            }
            else
            {
                return mixedFight((Monster)playCardOne, (Spell) playCardTwo);
            }
        }
        else if(playCardTwo instanceof Monster)
        {
            // cards switched so return also switched, switch back
            return mixedFight((Monster)playCardTwo, (Spell) playCardOne) == 1 ? 2 : 1;
        }
        else // two spells
        {
            return elementFight();
        }
    }

    public Integer monsterFight(Monster playCardOne, Monster playCardTwo) {
        /*    • Goblins are too afraid of Dragons to attack.
              • Wizzard can control Orks so they are not able to damage them.
              • The FireElves know Dragons since they were little and can evade their attacks.
        */

        if(playCardOne.getMonsterType() == MonsterType.DRAGON)
        {
            if(playCardTwo.getMonsterType() == MonsterType.GOBLIN)
            {
                return 1;
            }
            else if(playCardTwo.getMonsterType() == MonsterType.FIREELF)
            {
                // evade means win?
                return 2;
            }
        }
        else if(playCardTwo.getMonsterType() == MonsterType.DRAGON)
        {
            if(playCardOne.getMonsterType() == MonsterType.GOBLIN)
            {
                return 2;
            }
            else if(playCardOne.getMonsterType() == MonsterType.FIREELF)
            {
                // evade means win?
                return 1;
            }
        }
        else if(playCardOne.getMonsterType() == MonsterType.WIZZARD && playCardTwo.getMonsterType() == MonsterType.ORK) {
            return 1;
        }
        else if(playCardOne.getMonsterType() == MonsterType.ORK && playCardTwo.getMonsterType() == MonsterType.WIZZARD) {
            return 2;
        }
        return playCardOne.getDamage() > playCardTwo.getDamage() ? 1 : 2;

    }

    public Integer mixedFight(Monster playCardOne, Spell playCardTwo) {
        /*    • The armor of Knights is so heavy that WaterSpells make them drown them instantly.
              • The Kraken is immune against spells.
        */
        if(playCardOne.getMonsterType() == MonsterType.KNIGHT && playCardTwo.getElementType() == ElementType.WATER)
        {
            return 2;
        }
        else if(playCardOne.getMonsterType() == MonsterType.KRAKEN)
        {
            return 1;
        }
        else
        {
            if(playCardOne.getElementType() != playCardTwo.getElementType())
            {
                return elementFight();
            }
            else
            {
                return playCardOne.getDamage() > playCardTwo.getDamage() ? 1 : 2;
            }
        }

    }

    public Integer elementFight() {
        /*      • water -> fire
                • fire -> normal
                • normal -> water
         */
        if(playCardOne.getElementType() == ElementType.WATER)
        {
            if(playCardTwo.getElementType() == ElementType.FIRE)
            {
                return playCardOne.getDamage()*2 > playCardTwo.getDamage()/2 ? 1 : 2;
            }
            else if(playCardTwo.getElementType() == ElementType.NORMAL)
            {
                return playCardOne.getDamage()/2 > playCardTwo.getDamage()*2 ? 1 : 2;
            }
        }
        else if(playCardOne.getElementType() == ElementType.FIRE)
        {
            if(playCardTwo.getElementType() == ElementType.NORMAL)
            {
                return playCardOne.getDamage()*2 > playCardTwo.getDamage()/2 ? 1 : 2;
            }
            else if(playCardTwo.getElementType() == ElementType.WATER)
            {
                return playCardOne.getDamage()/2 > playCardTwo.getDamage()*2 ? 1 : 2;
            }
        }
        else if(playCardOne.getElementType() == ElementType.NORMAL)
        {
            if(playCardTwo.getElementType() == ElementType.WATER)
            {
                return playCardOne.getDamage()*2 > playCardTwo.getDamage()/2 ? 1 : 2;
            }
            else if(playCardTwo.getElementType() == ElementType.FIRE)
            {
                return playCardOne.getDamage()/2 > playCardTwo.getDamage()*2 ? 1 : 2;
            }
        }

        // else equal but should be checked before call
        return playCardOne.getDamage() > playCardTwo.getDamage() ? 1 : 2;

    }
}
