package at.technikum;

public abstract class Card {
    private final String name;
    private final Integer damage;
    private final ElementType elementType;
    private Boolean isInDeck;
    private String description;

    protected Card(String name, Integer damage, ElementType elementType) {
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.isInDeck = false;
        this.description = name + " has " + damage + " damage and is of ElementType " + elementType;
    }

    public String getName() {
        return name;
    }

    public Integer getDamage() {
        return damage;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public Boolean getIsInDeck() {
        return isInDeck;
    }

    public void changeIsInDeck() {
        isInDeck = !isInDeck;
    }

    public String getDescription() { return description; }

    protected void setDescription(String newDescription) {
        description = newDescription;
    }
}
