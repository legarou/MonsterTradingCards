package at.technikum;

public class BattleSpecialCases {

    private Card cardOne;
    private Card cardTwo;
    private String message;
    private int result;

    public BattleSpecialCases(Card userOne, Card userTwo) {
        this.cardOne = userOne;
        this.cardTwo = userTwo;
        this.message = "";
        this.result = -1;
    }

    public String getMessage() {
        return String.valueOf(message);
    }

    public void switchCards() {
        Card temp = cardOne;
        cardOne = cardTwo;
        cardTwo = temp;
    }

    // 0 = 3 -> nothing
    // 1 != 4
    // 1 -> player One wins
    // 4 -> player Two wins
    // 2 != 5
    // 2 -> player Two wins
    // 5 -> player One wins
    // 6 -> both are monsters with no special case
    public int checkForSpecialCase() {
        if(CardMonsterType.SPELL == cardOne.getMonsterType() && CardMonsterType.SPELL == cardTwo.getMonsterType()){
            return 0;
        }
        else if(CardMonsterType.SPELL != cardOne.getMonsterType() && CardMonsterType.SPELL != cardTwo.getMonsterType()){
            result = twoMonsters();
            if(result == 0) {
                switchCards();
                result = twoMonsters();
                if(result == 0){
                    return 6;
                }
                else {
                    return result + 3;
                }
            }
        }
        else {
            result = spellAndMonster();
            if(result == 0){
                switchCards();
                return spellAndMonster() + 3;
            }
            else {
                return result;
            }
        }

        return result;
    }

    public int twoMonsters() {
        /*    • Goblins are too afraid of Dragons to attack.
              • Wizzard can control Orks so they are not able to damage them.
              • The FireElves know Dragons since they were little and can evade their attacks.
        */
        if(cardOne.getMonsterType() == cardTwo.getMonsterType()) {
            return 6;
        }
        else if (CardMonsterType.DRAGON == cardOne.getMonsterType()) {
            if (CardMonsterType.GOBLIN == cardTwo.getMonsterType()) {
                message = "Goblins are too afraid of Dragons to attack!\n";
                return 1;
            } else if (CardMonsterType.FIREELF == cardTwo.getMonsterType()) {
                message = "The FireElves know Dragons since they were little and can evade their attacks.\n";
                return 2;
            } else {
                return 0;
            }
        }
        else if (CardMonsterType.WIZZARD == cardOne.getMonsterType() && CardMonsterType.ORK == cardTwo.getMonsterType()) {
            message = "Wizzards can control Orks! Orks are unable to attack or defend themselves.\n";
            return 1;
        }
        else {
            return 0;
        }

    }

    private int spellAndMonster() {
        /*    • The armor of Knights is so heavy that WaterSpells make them drown them instantly.
              • The Kraken is immune against spells.
        */
        // player "One" is Spell
        if(CardMonsterType.SPELL == cardOne.getMonsterType()) {
            if (CardMonsterType.KRAKEN == cardTwo.getMonsterType()) {
                message = "The Kraken is immune against spells!\n";
                return 2;
            }
            else if (CardElementType.WATER == cardOne.getElementType() && CardMonsterType.KNIGHT == cardTwo.getMonsterType()) {
                message = "The Knight's armor is too heavy!! Any WaterSpells make them drown instantly!\n";
                return 1;
            }
        }

        return 0;

    }
}
