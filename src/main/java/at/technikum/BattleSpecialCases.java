package at.technikum;

public class BattleSpecialCases {

    private CardMonsterType cardOneMonster;
    private CardMonsterType cardTwoMonster;
    private CardElementType cardOneElement;
    private CardElementType cardTwoElement;
    private String message;
    private int result;

    public BattleSpecialCases(CardMonsterType userOneMonster, CardElementType userOneElement, CardMonsterType userTwoMonster, CardElementType userTwoElement) {
        this.cardOneMonster = userOneMonster;
        this.cardTwoMonster = userTwoMonster;
        this.cardOneElement = userOneElement;
        this.cardTwoElement = userTwoElement;
        this.message = "";
        this.result = -1;
    }

    public String getMessage() {
        return String.valueOf(message);
    }

    public void switchCards() {
        CardMonsterType temp = cardOneMonster;
        cardOneMonster = cardTwoMonster;
        cardTwoMonster = temp;

        CardElementType temp2 = cardOneElement;
        cardOneElement = cardTwoElement;
        cardTwoElement = temp2;
    }

    // 0 -> nothing
    // 1 -> player One wins
    // 2 -> player Two wins
    // 3 -> both are monsters with no special case
    public int checkForSpecialCase() {
        if((CardMonsterType.SPELL == cardOneMonster) && (CardMonsterType.SPELL == cardTwoMonster)){
            return 0;
        }
        else if((cardOneMonster == cardTwoMonster)) {
            return 3;
        }
        else if((CardMonsterType.SPELL != cardOneMonster) && (CardMonsterType.SPELL != cardTwoMonster)){
            result = twoMonsters();
            if(result == 0) {
                switchCards();
                result = twoMonsters();
                if(result == 0){
                    return 3;
                }
                else {
                    return (result == 1) ? 2 : 1;
                }
            }
        }
        else {
            result = spellAndMonster();
            if(result == 0){
                switchCards();
                result = spellAndMonster();
                if(result != 0){
                    return (result == 1) ? 2 : 1;
                }
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
        if (CardMonsterType.DRAGON == cardOneMonster) {
            if (CardMonsterType.GOBLIN == cardTwoMonster) {
                message = "Goblins are too afraid of Dragons to attack!\n";
                return 1;
            } else if (CardMonsterType.FIREELF == cardTwoMonster) {
                message = "The FireElves know Dragons since they were little and can evade their attacks.\n";
                return 2;
            } else {
                return 0;
            }
        }
        else if (CardMonsterType.WIZZARD == cardOneMonster && CardMonsterType.ORK == cardTwoMonster) {
            message = "Wizzards can control Orks! Orks are unable to attack or defend themselves.\n";
            return 1;
        }
        else {
            return 0;
        }

    }

    public int spellAndMonster() {
        /*    • The armor of Knights is so heavy that WaterSpells make them drown them instantly.
              • The Kraken is immune against spells.
        */
        // player "One" is Spell
        if(CardMonsterType.SPELL == cardOneMonster) {
            if (CardMonsterType.KRAKEN == cardTwoMonster) {
                message = "The Kraken is immune against spells!\n";
                return 2;
            }
            else if ((CardElementType.WATER == cardOneElement) && (CardMonsterType.KNIGHT == cardTwoMonster)) {
                message = "The Knight's armor is too heavy!! Any WaterSpells make them drown instantly!\n";
                return 1;
            }
        }

        return 0;

    }
}
