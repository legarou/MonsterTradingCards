package at.technikum;

public class User {
    private String username;
    private Integer coins;
    private Stack cardStack;


    public User(String username) {
        this.username = username;
        this.coins = 20;
        this.cardStack = new Stack();
    }

    public String getUsername() {
        return username;
    }

    public Integer getCoins() {
        return coins;
    }

    public Stack getCardStack() {
        return cardStack;
    }

    public void buyPackage() {
        // TBD
    }

    public void tradeCard() {
        // TBD
    }

    public void battle() {
        // TBD
    }

    public void manageCards() {
        // TBD
    }


}
