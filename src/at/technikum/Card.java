package at.technikum;

public abstract class Card {
    private final String name;
    private final Integer damage;
    private final ElementType elementType;
    private Boolean isInDeck;

    protected Card(String name, Integer damage, ElementType elementType) {
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
        this.isInDeck = false;
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
}
