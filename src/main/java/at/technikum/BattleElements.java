package at.technikum;

public class BattleElements {
    private Card cardOne;
    private Card cardTwo;
    private String message;

    public BattleElements(Card userOne, Card userTwo) {
        this.cardOne = userOne;
        this.cardTwo = userTwo;
        this.message = "";
    }

    public void switchCards() {
        Card temp = cardOne;
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

        if(cardOne.getElementType() == cardTwo.getElementType()) {
            return 0;
        }
        else if (CardElementType.WATER == cardOne.getElementType()) {
            if (CardElementType.FIRE == cardTwo.getElementType()) {
                message = "Water trumps Fire!\n";
                return  1;
            } else if (CardElementType.NORMAL == cardTwo.getElementType()) {
                message = "Normal trumps Water!\n";
                return 2;
            }
        }
        else if (CardElementType.FIRE == cardOne.getElementType()) {
            if (CardElementType.NORMAL == cardTwo.getElementType()) {
                message = "Fire trumps Normal!\n";
                return 1;
            } else if (CardElementType.WATER == cardTwo.getElementType()) {
                message = "Water trumps Fire!\n";
                return 2;
            }
        }
        else if (CardElementType.NORMAL == cardOne.getElementType()) {
            if (CardElementType.WATER == cardTwo.getElementType()) {
                message = "Normal trumps Water!\n";
                return 1;
            } else if (CardElementType.FIRE == cardTwo.getElementType()) {
                message = "Fire trumps Normal!\n";
                return 2;
            }
        }

        // unreachable
        return -1;

    }
}
