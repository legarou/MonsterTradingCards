package at.technikum;

public class BattleElements {
    private CardElementType cardOne;
    private CardElementType cardTwo;
    private String message;

    public BattleElements(CardElementType userOne, CardElementType userTwo) {
        this.cardOne = userOne;
        this.cardTwo = userTwo;
        this.message = "";
    }

    public void switchCards() {
        CardElementType temp = cardOne;
        cardOne = cardTwo;
        cardTwo = temp;
    }

    public String getMessage() {
        return String.valueOf(message);
    }

    // 0  -> nothing
    // 1 -> player One has
    // 2 -> player Two has
    public int checkForTrump() {
        /*      • water -> fire
                • fire -> normal
                • normal -> water
         */

        if(cardOne == cardTwo) {
            return 0;
        }
        else if (CardElementType.WATER == cardOne) {
            if (CardElementType.FIRE == cardTwo) {
                message = "Water trumps Fire!\n";
                return  1;
            } else if (CardElementType.NORMAL == cardTwo) {
                message = "Normal trumps Water!\n";
                return 2;
            }
        }
        else if (CardElementType.FIRE == cardOne) {
            if (CardElementType.NORMAL == cardTwo) {
                message = "Fire trumps Normal!\n";
                return 1;
            } else if (CardElementType.WATER == cardTwo) {
                message = "Water trumps Fire!\n";
                return 2;
            }
        }
        else if (CardElementType.NORMAL == cardOne) {
            if (CardElementType.WATER == cardTwo) {
                message = "Normal trumps Water!\n";
                return 1;
            } else if (CardElementType.FIRE == cardTwo) {
                message = "Fire trumps Normal!\n";
                return 2;
            }
        }

        // unreachable
        return -1;

    }
}
