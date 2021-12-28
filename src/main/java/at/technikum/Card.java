package at.technikum;

public abstract class Card {
    private final String name;
    private final int damage;
    private final ElementType elementType;

    protected Card(String name, Integer damage, ElementType elementType) {
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public ElementType getElementType() {
        return elementType;
    }

    @Override
    public String toString() {
        return name + " has " + damage + " damage and is of ElementType " + elementType;
    }
}
